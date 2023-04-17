<p>
  <a href="https://piiano.com/pii-data-privacy-vault/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://piiano.com/docs/img/logo-developers-dark.svg">
      <source media="(prefers-color-scheme: light)" srcset="https://piiano.com/wp-content/uploads/piiano-logo-developers.png">
      <img alt="Piiano Vault" src="https://piiano.com/wp-content/uploads/piiano-logo-developers.png" height="40" />
    </picture>
  </a>
</p>

# Demo application using Piiano vault hibernate encryption

Replacing the sensitive personal information in the application DB with vault encrypted values.

At this project the vault is being integrated transparently to the application at the ORM level.
This level of integration is suitable for applications that are using ORM to access the DB, when other use cases of accessing the DB directly should be handled at the application level.

## Requirements

1. Java JDK 1.8+
1. Maven (3.8.3+)/Gradle (7.2+)
1. Docker
1. Vault license - get a free trial license from [get-started](https://piiano.com/docs/guides/get-started) and set it to an environment variable `PVAULT_SERVICE_LICENSE`. 
1. Mac or Linux machine. For Windows, you will need a WSL2 console.

## Compiling the application

Run: `mvn clean install -DskipTests`  
To run it with tests: `mvn clean install`

**NOTE** To run tests you need to first run vault. See [get-started](https://piiano.com/docs/guides/get-started) guide.

## Running the application

The demo application can be either run automatically by our provided script or manually.  
**Optional:** configure non-default network parameters by setting `APP_PORT`, `MYSQL_PORT`, and/or `DOCKER_LOCALHOST` (e.g. to 172.17.0.1 if `host.docker.internal` isn't working).

### One liner application running

Run locally using `./run.sh`.  
To enable interactive mode, run `./run.sh -i`. In this mode you will need to press `<enter>` after every action.
  
### Running the application manually

1. Run: `mvn clean install -DskipTests`
1. Run Mysql:
    ```commandLine
    docker run --name mysql -e MYSQL_ROOT_PASSWORD=rootpass -p 3306:3306  -d  mysql:8.0.30
    ```
   You will require mysql CLI. If you don't have it installed you can run it from the mysql container: `docker exec -it mysql mysql`
1. Create the mysql Database ([taken from here](https://spring.io/guides/gs/accessing-data-mysql/#initial)):
    ```commandLine
    mysql --password
    create database app_db;
    create user 'springuser'@'%' identified by 'userpass';
    grant all on app_db.* to 'springuser'@'%';
    ```
    Note that the app connects to mysql using configuration in src/main/resources/application.properties
    ```
      spring.datasource.url=jdbc:mysql://localhost:3306/app_db
      spring.datasource.username=springuser
      spring.datasource.password=userpass
    ```
1. Install Piiano vault
   1. Install the Piiano Vault docker image by following the first step of the [get-started](https://piiano.com/docs/guides/get-started) guide.
   2. Create a collection for the customer's sensitive data
    ```
    pvault collection add --collection-pvschema "
    customers PERSONS (
      name NAME,
      phone PHONE_NUMBER
    )"
    ```
1. Run the Piiano ORM Demo:
    ```
    java -jar ~/.m2/repository/com/piiano/example/demo-app-hibernate-encryption/0.0.1-SNAPSHOT/demo-app-hibernate-encryption-0.0.1-SNAPSHOT.jar
    ```
1. Add customers by calling the REST API and filling the details:
    ```
    http://localhost:8090/add-customer?name=${name}&email=${email}&phone=${phone}&ssn=${ssn}&dob=${dob}&state=${state}
    ```

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
