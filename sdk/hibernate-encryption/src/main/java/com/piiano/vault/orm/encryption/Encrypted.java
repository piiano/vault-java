package com.piiano.vault.orm.encryption;

import com.piiano.vault.client.openapi.model.EncryptionType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

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
	private String requestedProperty;

	private final Encryptor encryptor;

	public Encrypted() {
		encryptor = new Encryptor();
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
			String value = rs.getString(names[0]);
			if (encryptor.isEncrypted(value)) {
				value = encryptor.decrypt(this.collectionName, this.requestedProperty, value).toString();
			}
			return value;
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {

		try {
			String propValue = null;
			if (value != null) {
				propValue = value.toString();

				if (!encryptor.isEncrypted(propValue)) {
					propValue = encryptor.encrypt(encryptionType, collectionName, propertyName, propValue);
				}
			}

			st.setString(index, propValue);
		} catch (Exception e) {
			throw new HibernateException(e);
		}
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
		this.requestedProperty = this.propertyName;
		if (this.propertyName.indexOf(".") > 0) {
			this.propertyName = this.propertyName.substring(0, this.propertyName.indexOf("."));
		}
	}
}
