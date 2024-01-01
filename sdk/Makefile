.DEFAULT_GOAL := all

VAULT_VERSION ?= 1.9.7
OPENAPI_YAML := openapi.yaml
CODEGEN_IMAGE := openapitools/openapi-generator-cli:v6.4.0
CODEGEN_CONFIG_FILE := config.yaml
OPENAPI_FOLDER := openapi
CLIENT_FOLDER := client
HIBERNATE_FOLDER := hibernate-encryption
APP_FOLDER := ../examples/demo-app-hibernate-encryption


$(OPENAPI_YAML):
	curl -L https://docs.piiano.com/v$(subst .,-,$(VAULT_VERSION))/assets/openapi.yaml --output $@

$(OPENAPI_FOLDER)/pom.xml: $(OPENAPI_YAML) $(CODEGEN_CONFIG_FILE)
	mkdir -p $(OPENAPI_FOLDER)/target
	docker run --rm -v "${PWD}:/pwd" $(CODEGEN_IMAGE) generate \
            -g java \
            -i /pwd/$(OPENAPI_YAML) \
            -c /pwd/$(CODEGEN_CONFIG_FILE) \
            -o /pwd/$(OPENAPI_FOLDER)

.PHONY: generate-openapi
generate-openapi: $(OPENAPI_YAML) $(OPENAPI_FOLDER)/pom.xml
	mvn -f $(OPENAPI_FOLDER) clean install -DskipTests

.PHONY: $(CLIENT_FOLDER)
$(CLIENT_FOLDER): generate-openapi
	mvn -f $(CLIENT_FOLDER) clean install -DskipTests

.PHONY: $(HIBERNATE_FOLDER)
$(HIBERNATE_FOLDER): $(CLIENT_FOLDER)
	mvn -f $(HIBERNATE_FOLDER) clean install -DskipTests

.PHONY: $(APP_FOLDER)
$(APP_FOLDER): $(HIBERNATE_FOLDER)
	mvn -f $(APP_FOLDER) clean install -DskipTests

.PHONY: run-vault
run-vault:
	docker rm -f pvault-dev
	docker run --init \
		 --name pvault-dev \
		 --add-host='host.docker.internal:host-gateway' \
		 -p 8123:8123 \
		 -e PVAULT_SERVICE_LICENSE=${PVAULT_SERVICE_LICENSE} \
		 -e PVAULT_SENTRY_ENABLE=true \
		 -e PVAULT_LOG_CUSTOMER_IDENTIFIER="piiano" \
		 -e PVAULT_LOG_CUSTOMER_ENV="VAULT-JAVA" \
		 -d \
		 piiano/pvault-dev:$(VAULT_VERSION)
	sleep 5

.PHONY: test-openapi
test-openapi: generate-openapi
	mvn -f $(OPENAPI_FOLDER) test

.PHONY: test-client
test-client: $(CLIENT_FOLDER)
	mvn -f $(CLIENT_FOLDER) test

.PHONY: test-hibernate
test-hibernate: $(HIBERNATE_FOLDER)
	mvn -f $(HIBERNATE_FOLDER) test

.PHONE: test-app
test-app: $(APP_FOLDER)
	mvn -f $(APP_FOLDER) test

.PHONY: test-all
test-all: test-openapi run-vault test-client test-hibernate test-app

.PHONY: clean
clean:
	mvn -f $(CLIENT_FOLDER) clean
	mvn -f $(HIBERNATE_FOLDER) clean
	mvn -f $(APP_FOLDER) clean
	rm -f -r $(OPENAPI_FOLDER)
	rm -f $(OPENAPI_YAML)

.PHONY: all
all: $(APP_FOLDER)
