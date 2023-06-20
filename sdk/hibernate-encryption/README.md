<p>
  <a href="https://piiano.com/pii-data-privacy-vault/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://piiano.com/docs/img/logo-developers-dark.svg">
      <source media="(prefers-color-scheme: light)" srcset="https://piiano.com/wp-content/uploads/piiano-logo-developers.png">
      <img alt="Piiano Vault" src="https://piiano.com/wp-content/uploads/piiano-logo-developers.png" height="40" />
    </picture>
  </a>
</p>

# Piiano vault Hibernate encryption

This package is compatible with Vault 1.6.1

## Compiling

Run: `mvn clean install -DskipTests`

## Testing

You will need to start Vault first. It is recommended to run testing from the Makefile in the [parent folder](./..).

### Vault ORM Annotations

The hibernate-encryption jar contains the following annotations that you can use to integrate your entities seamlessly with Vault:
- `@Type(type = "Encrypted")` - marks a field as encrypted.  
  The field will be encrypted when persisted to the DB and decrypted when retrieved from the DB.  
  You can optionally specify the name of the collection to use for encryption like so:  
  `@Type(type = "Encrypted", parameters = {@Parameter(name = COLLECTION, value = "customers")})`  
  If the collection name is not specified, the name of the Table specified in the `@Table` annotation will be used.
- `@Transformation` - marks a field as a transformation of an encrypted property.
  The field will be automatically be decrypted and transformed by Vault when retrieved from the DB.  
  You must specify the name of the encrypted property whose value will be transformed and the transformer to apply like so:   
  `@Transformation(property = "email", transformer = "mask")`  
  This will decrypt the encrypted value of the `email` property and apply the `mask` transformer to it.  
  Note: You must also apply the `@Transient` annotation to the field so that it is not persisted to the DB.

In order to use the `@Transformation` annotation, you must inject the `TransformerInterceptor` bean into your application context.
This can be done by adding the following to your Spring configuration:

```java
@Configuration
@ComponentScan(" com.piiano.vault.orm.encryption")
public class InterceptorConfig {
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(TransformationInterceptor interceptor) {
        return props -> props.put("hibernate.session_factory.interceptor", interceptor);
    }
}
```

`TransformerInterceptor` intercepts the loading of all entities from the DB and decrypts and transforms the fields marked with the `@Transformation` annotation.
It is not required if you are not using the `@Transformation` annotation in any field.
