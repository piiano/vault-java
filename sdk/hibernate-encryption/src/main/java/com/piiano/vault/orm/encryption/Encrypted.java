package com.piiano.vault.orm.encryption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.CryptoClient;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DecryptParams;
import com.piiano.vault.client.model.DefaultParams;
import com.piiano.vault.client.model.EncryptParams;
import com.piiano.vault.client.openapi.model.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static com.piiano.vault.client.VaultClient.getPvaultClient;

/**
 * This class is used to tokenize a field of an entity. it implements the UserType interface
 * This class implements the nullSafeSet method which is called by the ORM before persisting the entity and
 * before executing the find queries
 * In the implementation of the nullSafeSet method, the field value is being replaced by the token id and
 * is set to the field.
 * The token id is calculated before the entity is persisted and is stored in the vault by using the hash
 * method of the TokenApi which retrieves equal token id for deterministic token for the same value.
 */
public class Encrypted implements UserType, DynamicParameterizedType {

	public static final String TYPE = "type";
	public static final String COLLECTION = "collection";
	public static final String PROPERTY = "property";

	private EncryptionType encryptionType;
	private String collectionName;
	private String propertyName;

	private final CryptoClient cryptoClient;

	public Encrypted() {
		cryptoClient = new CryptoClient(getPvaultClient(), DefaultParams.builder()
				.accessReason(AccessReason.AppFunctionality).build());
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public Class<String> returnedClass() {
		return String.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x != null ? x.hashCode() : 0;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);
		if ((deepCopy instanceof Serializable)) {
			return (Serializable) deepCopy;
		}
		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		if (rs.wasNull()) {
			return null;
		}
		try {
			DecryptionRequest request = new DecryptionRequest().encryptedObject(
					new EncryptedObjectInput().ciphertext(rs.getString(names[0])));
			return cryptoClient.decrypt(
							ImmutableList.of(request),
							Collections.emptySet(),
							DecryptParams.builder().collection(this.collectionName).build())
					.get(0).getFields().get(this.propertyName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {

		String propValue = null;
		if (value != null) {
			propValue = value.toString();
			if (!isEncrypted(propValue)) {
				EncryptionRequest request = new EncryptionRequest()
						.type(this.encryptionType)
						._object(new InputObject().fields(ImmutableMap.of(this.propertyName, propValue)));
				try {
					propValue = cryptoClient.encrypt(
							ImmutableList.of(request),
							EncryptParams.builder().collection(this.collectionName).build())
							.get(0).getCiphertext();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		st.setString(index, propValue);
	}

	private boolean isEncrypted(String propValue) {
		return propValue.startsWith("encrypted");
	}

	@Override
	public void setParameterValues(Properties parameters) {
		if (EncryptionType.DETERMINISTIC.toString().equalsIgnoreCase(parameters.getProperty(TYPE))) {
			this.encryptionType = EncryptionType.DETERMINISTIC;
		} else {
			this.encryptionType = EncryptionType.RANDOMIZED;
		}
		this.collectionName = parameters.getProperty(COLLECTION);
		this.propertyName = parameters.getProperty(PROPERTY);
	}
}
