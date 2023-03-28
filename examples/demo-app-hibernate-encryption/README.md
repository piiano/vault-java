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

1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)
3. Compiled `demo-app-hibernate-encryption-0.0.1-SNAPSHOT.jar` (run `make` in the parent directory)
4. Docker

## Installation

To run the Vault you will need to issue a free 30-days license from [get-started](https://piiano.com/docs/guides/get-started) and set it to an environment variable `PVAULT_SERVICE_LICENSE`. 

Run locally using `./run.sh` from a Mac or Linux machine, or inside a WSL2 console on a Windows machine.
To enable interactive mode, run `./run.sh -i`.
Optionally: configure non-default network parameters by setting `APP_PORT`, `MYSQL_PORT`, `PVAULT_PORT`, `PSQL_PORT`, and/or `DOCKER_LOCALHOST` (e.g. to 172.17.0.1 if `host.docker.internal` isn't working).

### Alternative Manual Instructions

The boot
1. Mysql:
- docker run --name mysql -e MYSQL_ROOT_PASSWORD=rootpass -p 3306:3306  -d  mysql:8.0.30
- You will require mysql CLI. If you don't have it installed you can run it from the mysql container: `docker exec -it mysql mysql`
- Create the mysql Database ([taken from here](https://spring.io/guides/gs/accessing-data-mysql/#initial)):
```
mysql --password
create database app_db;
create user 'springuser'@'%' identified by 'userpass';
grant all on app_db.* to 'springuser'@'%';
```
- the app connects to mysql using configuration in src/main/resources/application.properties
      spring.datasource.url=jdbc:mysql://localhost:3306/app_db
      spring.datasource.username=springuser
      spring.datasource.password=userpass

2. Piiano vault
- Install Piiano Vault - step 1 & 2 on [get-started](https://piiano.com/docs/guides/get-started)
- Create collection for user's sensitive data
```
pvault collection add --collection-pvschema "
users PERSONS (
  name NAME,
  phone_number PHONE_NUMBER
)"
```

3. Run the Piiano ORM Demo:
```
java -jar ~/.m2/repository/com/piiano/example/demo-app-hibernate-encryption/0.0.1-SNAPSHOT/demo-app-hibernate-encryption-0.0.1-SNAPSHOT.jar
```
