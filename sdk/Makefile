.DEFAULT_GOAL := all

.PHONY: all
all: $(OPENAPI_YAML) generate-openapi

VAULT_VERSION := v1.2.2
OPENAPI_YAML := openapi.yaml
GEN_FOLDER := openapi

CODEGEN_IMAGE := openapitools/openapi-generator-cli:v6.4.0
CODEGEN_CONFIG_FILE := config.yaml


$(OPENAPI_YAML):
	curl https://piiano.com/docs/$(subst .,-,$(VAULT_VERSION))/assets/openapi.yaml --output $@

$(GEN_FOLDER)/pom.xml: $(OPENAPI_YAML) $(CODEGEN_CONFIG_FILE)
	docker run --rm -u $(id -u):$(id -g) -v "${PWD}:/pwd" $(CODEGEN_IMAGE) generate \
        -g java \
        -i pwd/$(OPENAPI_YAML) \
        -c pwd/$(CODEGEN_CONFIG_FILE) \
        -p
        -o pwd/$(GEN_FOLDER)

.PHONY: generate-openapi
generate-openapi: $(GEN_FOLDER)/pom.xml
