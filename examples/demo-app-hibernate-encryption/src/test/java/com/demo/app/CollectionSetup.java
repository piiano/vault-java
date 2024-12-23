package com.demo.app;

import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.CollectionsApi;
import com.piiano.vault.client.openapi.Configuration;
import com.piiano.vault.client.openapi.model.Collection;
import com.piiano.vault.client.openapi.model.Property;

import java.util.Collections;

public class CollectionSetup {

    private static final String collectionName = "customers";

    public static void setUp() throws ApiException {
        addCollection();
    }

    public static void tearDown() {
        deleteCollectionIfExists();
    }

    private static Collection addCollection() throws ApiException {

        ApiClient pvaultClient = getApiClient();
        CollectionsApi collectionsApi = new CollectionsApi(pvaultClient);

        deleteCollectionIfExists();

        Collection collection = createCollection();
        return collectionsApi.addCollection(collection).format("json").execute();
    }

    private static void deleteCollectionIfExists() {
        ApiClient pvaultClient = getApiClient();
        CollectionsApi collectionsApi = new CollectionsApi(pvaultClient);

        try {
            collectionsApi.deleteCollection(collectionName).execute();
        } catch (ApiException e) {
            // Collection not found - do nothing.
        }
    }

    public static Collection createCollection() {
        return new Collection()
                .name(collectionName)
                .type(Collection.TypeEnum.PERSONS)
                .addPropertiesItem(
                        new Property().name("name").dataTypeName("NAME").description("Name")
                                .isEncrypted(true)
                ).addPropertiesItem(
                        new Property().name("phone").dataTypeName("PHONE_NUMBER").description("Phone")
                                .isEncrypted(true).isNullable(true)
                ).addPropertiesItem(
                        new Property().name("ssn").dataTypeName("SSN").description("ssn")
                            .isEncrypted(true).isNullable(true)
                ).addPropertiesItem(
                new Property().name("email").dataTypeName("EMAIL").description("ssn")
                    .isEncrypted(true).isNullable(true)
            );
    }

    private static ApiClient getApiClient() {
        // Create configuration, bearer auth and client API
        ApiClient pvaultClient = Configuration.getDefaultApiClient();
        pvaultClient.setBasePath("http://localhost:8123");
        pvaultClient.setBearerToken("pvaultauth");
        pvaultClient.addDefaultHeader("Content-Type", "application/json");
        return pvaultClient;
    }
}
