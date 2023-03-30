package com.piiano.vault.orm.encryption;

import com.piiano.vault.client.openapi.model.EncryptionType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
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

	private Encryptor encryptor;

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

		try {
			String value = rs.getString(names[0]);
			if (encryptor.isEncrypted(value)) {
				value = encryptor.decrypt(value).toString();
			}
			return value;
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {

		if (value == null) {
			st.setNull(index, Types.VARCHAR);
			return;
		}

		try {
			String propValue = value.toString();

			if (propValue != null && !encryptor.isEncrypted(propValue)) {
				propValue = encryptor.encrypt(propValue);
			}

			st.setString(index, propValue);
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public void setParameterValues(Properties parameters) {

		EncryptionType encryptionType = EncryptionType.DETERMINISTIC;
		if (EncryptionType.RANDOMIZED.toString().equalsIgnoreCase(parameters.getProperty(TYPE))) {
			encryptionType = EncryptionType.RANDOMIZED;
		}

		ParameterType parameterType = (ParameterType) parameters.get(DynamicParameterizedType.PARAMETER_TYPE);

		String collection = parameters.getProperty(COLLECTION) != null ?
				parameters.getProperty(COLLECTION) :
				parameterType.getTable();

		String propertyName = parameters.getProperty(PROPERTY);
		if (propertyName == null) {
			Optional<String> columnName =
					Arrays.stream(parameterType.getAnnotationsMethod())
							.filter(a -> a.annotationType().isAssignableFrom(Column.class))
							.map(c -> ((Column) c).name())
							.findFirst();

			if (columnName.isPresent()) {
				propertyName = columnName.get();
			}
		}

		encryptor = new Encryptor(encryptionType, collection, propertyName);
	}
}
