package com.piiano.vault.orm.encryption;

import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.EncryptionType;
import lombok.AllArgsConstructor;
import org.hibernate.EmptyInterceptor;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.Type;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransformationInterceptor extends EmptyInterceptor {

    // Intercept the loading of the entity.
    // onLoad is called after the Encrypted types are decrypted.
    // In Encrypted.nullSafeGet, (see saveEncryptedValueOnOwner) the Encrypted instance set the encrypted value
    // on the respective properties of the owner which is passed here in the first argument.
    // onLoad reflects on the entity and discovers all the properties that are annotated with @Transformation.
    // Let's call those the transformation fields.
    // The Transformation annotation specifies the name of the encrypted field and the name of the transformer
    // that should be applied to the decrypted value of that field in order to set the value of the transformation field.
    // For each transformation field, the interceptor creates an instance of Encryptor and decrypts the value of the
    // encrypted field specifying the transformer that should be applied. It then sets the value of the transformation
    // field with the result.
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        try {
            calculateTransformationFields(entity);
        } catch (ApiException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @AllArgsConstructor
    static class TransformationField {
        Field entityField; // The field defined in the entity that is annotated with Transformation (e.g. "ssnMask")
        String dottedName; // The transformation binding that will be passed to Vault to get the value of the field (e.g. "ssn.mask")
    }

    static class TransformationMappings {
        TransformationMappings(Field encryptedField) {
            this.encryptedField = encryptedField;
            this.transformations = new ArrayList<>();
        }

        Field encryptedField;
        List<TransformationField> transformations;
    }

    private static void calculateTransformationFields(Object entity) throws ApiException, IllegalAccessException {
        Class<?> clazz = entity.getClass();

        // the table name is the default collection name unless overridden by the Encrypted annotation
        calculateTransformationFields(
            entity,
            getTableName(clazz),
            findTransformations(clazz));
    }

    @NotNull
    private static Map<String, TransformationMappings> findTransformations(Class<?> clazz) {

        // get all fields of the entity - including the transient ones that are not persisted.
        // (these are assumed to be transformation fields)
        Map<String, Field> allFields = Arrays.stream(clazz.getDeclaredFields())
            .collect(Collectors.toMap(
                Field::getName,
                field -> field));

        // find all the transformation fields and create a map of the encrypted field name to the transformation fields
        // where the transformation field is a transformation of the encrypted field.
        Map<String, TransformationMappings> transformationMap = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (isTransformationAnnotated(field)) {
                Transformation transformationAnnotation = getTransformationField(field);

                TransformationMappings mappings = getTransformationMappings(
                    transformationMap,
                    allFields,
                    transformationAnnotation.property());

                mappings.transformations.add(
                    getTransformationField(
                        field,
                        transformationAnnotation));
            }
        }
        return transformationMap;
    }

    private static void calculateTransformationFields(
        Object entity,
        String defaultCollectionName,
        Map<String, TransformationMappings> transformationMap) throws IllegalAccessException, ApiException {

        for (TransformationMappings mappings : transformationMap.values()) {

            Field encryptedField = mappings.encryptedField;

            // The collection name is the default collection name unless overridden by the Encrypted annotation
            String collectionName = getUpdatedCollectionName(defaultCollectionName, encryptedField);

            // For each transformation field, the interceptor creates an instance of Encryptor and decrypts the value of the
            for (TransformationField transformation : mappings.transformations) {
                // Extract the encrypted value from the entity that was placed on the encrypted field by the Encrypted type
                String encryptedValue = getFieldValue(entity, encryptedField);
                if (encryptedValue == null) {
                    // If the encrypted value is null (was not set), the transformation cannot be applied.
                    continue;
                }
                // Decrypt the value using an Encryptor that calls Vault specifying the dotted name of the transformation.
                String decryptedValue = getDecryptedValue(collectionName, transformation.dottedName, encryptedValue);
                // Set the decrypted result of the transformation on the transformation field
                setFieldValue(transformation.entityField, entity, decryptedValue);
            }
        }
    }

    private static String getUpdatedCollectionName(String defaultName, Field field) {
        // The collection name is the default collection name unless overridden by the Encrypted annotation
        if (isTypeAnnotated(field)) {
            for (Parameter parameter : getTypeAnnotation(field).parameters()) {
                if (isCollectionParameter(parameter)) {
                    return parameter.value();
                }
            }
        }
        return defaultName;
    }

    // Create an Encryptor that calls Vault specifying the dotted name of the transformation and decrypt returning the transformed value.
    private static String getDecryptedValue(String collectionName, String dottedName, String encryptedValue) throws ApiException {
        Encryptor encryptor = new Encryptor(
            EncryptionType.DETERMINISTIC,
            collectionName,
            dottedName);
        return encryptor.decrypt(encryptedValue).toString();
    }

    @NotNull
    private static TransformationField getTransformationField(Field field, Transformation transformation) {
        return new TransformationField(
            field,
            String.format("%s.%s", transformation.property(), transformation.transformer()));
    }

    private static TransformationMappings getTransformationMappings(
        Map<String, TransformationMappings> transformationMap,
        Map<String, Field> allFields,
        String encryptedPropertyName) {

        if (transformationMap.containsKey(encryptedPropertyName)) {
            return transformationMap.get(encryptedPropertyName);
        }

        // If this is the first transformation of the encryptedPropertyName, create a new mapping.
        Field encryptedField = allFields.get(encryptedPropertyName);

        TransformationMappings mappings = new TransformationMappings(encryptedField);

        // Add the mapping to the map of mappings
        transformationMap.put(encryptedPropertyName, mappings);

        return mappings;
    }

    private static void setFieldValue(Field entityField, Object entity, String decryptedValue) throws IllegalAccessException {
        entityField.setAccessible(true);
        entityField.set(entity, decryptedValue);
    }

    private static String getFieldValue(Object entity, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(entity);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    // TODO: Assumes that the Type annotation is only used for the Encrypted type. This is not necessarily true.
    private static boolean isTypeAnnotated(Field field) {
        return field.isAnnotationPresent(org.hibernate.annotations.Type.class);
    }

    private static org.hibernate.annotations.Type getTypeAnnotation(Field field) {
        return field.getAnnotation(org.hibernate.annotations.Type.class);
    }

    private static boolean isTransformationAnnotated(Field field) {
        return field.isAnnotationPresent(Transformation.class);
    }

    private static Transformation getTransformationField(Field field) {
        return field.getAnnotation(Transformation.class);
    }

    // We support getting the collection name from the Table annotation or the Encrypted annotation.
    // Get the table name here. It may be overridden by the Encrypted annotation.
    private static String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(javax.persistence.Table.class)) {
            return clazz.getAnnotation(javax.persistence.Table.class).name();
        }
        return "";
    }

    private static boolean isCollectionParameter(Parameter parameter) {
        return parameter.name().equals(Encrypted.COLLECTION);
    }
}
