.DEFAULT_GOAL := all

VAULT_VERSION ?= 1.3.1
OPENAPI_YAML := openapi.yaml
GEN_FOLDER := openapi
CLIENT_FOLDER := client
HIBERNATE_FOLDER := hibernate-encryption
APP_FOLDER := ../examples/demo-app-hibernate-encryption
CODEGEN_IMAGE := openapitools/openapi-generator-cli:v6.4.0
CODEGEN_CONFIG_FILE := config.yaml

$(OPENAPI_YAML):
	curl https://piiano.com/docs/v$(subst .,-,$(VAULT_VERSION))/assets/openapi.yaml --output $@

$(GEN_FOLDER)/pom.xml: $(OPENAPI_YAML) $(CODEGEN_CONFIG_FILE)
	docker run --rm -v "${PWD}:/pwd" $(CODEGEN_IMAGE) generate \
            -g java \
            -i /pwd/$(OPENAPI_YAML) \
            -c /pwd/$(CODEGEN_CONFIG_FILE) \
            -o /pwd/$(GEN_FOLDER)

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

.PHONY: generate-openapi
generate-openapi: $(OPENAPI_YAML) $(GEN_FOLDER)/pom.xml

.PHONY: $(CLIENT_FOLDER)
$(CLIENT_FOLDER): generate-openapi
	cd client && mvn clean install -DskipTests && cd -

.PHONY: $(HIBERNATE_FOLDER)
$(HIBERNATE_FOLDER): $(CLIENT_FOLDER)
	cd hibernate-encryption && mvn clean install -DskipTests && cd -

.PHONE: $(APP_FOLDER)
$(APP_FOLDER): $(HIBERNATE_FOLDER)
	cd $(APP_FOLDER) && mvn clean install -DskipTests && cd -

.PHONY: all
all: $(APP_FOLDER)

.PHONY: test-openapi
test-openapi: run-vault $(CLIENT_FOLDER)
	cd $(GEN_FOLDER) && mvn -X test && cd -

.PHONY: test-client
test-client: run-vault $(CLIENT_FOLDER)
	cd $(CLIENT_FOLDER) && mvn test && cd -

.PHONY: test-hibernate
test-hibernate: run-vault $(HIBERNATE_FOLDER)
	cd $(HIBERNATE_FOLDER) && mvn test && cd -

.PHONE: test-app
test-app: run-vault $(APP_FOLDER)
	cd ../examples/demo-app-hibernate-encryption && mvn test && cd -

.PHONY: test
test: run-vault test-hibernate test-client test-openapi

.PHONY: clean
clean:
	cd $(CLIENT_FOLDER) && mvn clean && cd -
	cd $(HIBERNATE_FOLDER) && mvn clean && cd -
	cd $(GEN_FOLDER) && mvn clean && cd -
	rm -f -r $(GEN_FOLDER)
	rm -f $(OPENAPI_YAML)
