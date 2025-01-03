package com.piiano.vault.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.piiano.vault.client.CollectionSetup.collectionName;
import static com.piiano.vault.client.DefaultClient.getDefaultClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VaultClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final VaultClient vaultClient = new VaultClient(apiClient);

    @BeforeEach
    public void beforeEach() throws ApiException {
        CollectionSetup.setUp();
    }

    @AfterEach
    public void afterEach() {
        CollectionSetup.tearDown();
    }

    @Test
    public void objectOperationsTest() throws ApiException {

        // Add an object
        String propName = "name";
        String name = "John";
        Map<String, Object> fields = ImmutableMap.of(propName, name);

        ObjectID objectID = vaultClient.objectClient().addObject(
                AddObjectParams.builder()
                        .collection(collectionName)
                        .fields(fields)
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertNotNull(objectID);
        assertNotNull(objectID.getId());

        // Get the object
        Map<String, Object> objectFields = vaultClient.objectClient().getObject(
                GetObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertEquals(1, objectFields.size());
        assertEquals(name, objectFields.get(propName));

        // Update the object
        String newName = "Jane";
        vaultClient.objectClient().updateObject(
                UpdateObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .fields(ImmutableMap.of(propName, newName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        // get the object again and validate the updated value
        objectFields = vaultClient.objectClient().getObject(
                GetObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertEquals(1, objectFields.size());
        assertEquals(newName, objectFields.get(propName));

        // Search the object
        ObjectFieldsPage objectsPage = vaultClient.objectClient().searchObjects(
                SearchObjectsParams.builder()
                        .collection(collectionName)
                        .query(new Query().match(ImmutableMap.of("id", objectID.getId())))
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertEquals(1, objectsPage.getResults().size());
        assertEquals(newName, objectsPage.getResults().get(0).get(propName));

        vaultClient.objectClient().deleteObject(
                DeleteObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        // Search the token again and verify it is deleted
        objectsPage = vaultClient.objectClient().searchObjects(
                SearchObjectsParams.builder()
                        .collection(collectionName)
                        .query(new Query().match(ImmutableMap.of("id", objectID.getId())))
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        assertEquals(0, objectsPage.getResults().size());
    }


    @Test
    public void tokenizeTest() throws ApiException {
        TokenizeRequest request = new TokenizeRequest()
                .type(TokenType.DETERMINISTIC)
                ._object(new InputObject().fields(ImmutableMap.of("name", "John")));

        List<TokenValue> result = vaultClient.tokenClient().tokenize(
                TokenizeParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenizeRequest(ImmutableList.of(request))
                        .build());
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getTokenId());
    }

    @Test
    public void encryptionTest() throws ApiException {
        EncryptionRequest request = new EncryptionRequest()
                .type(EncryptionType.DETERMINISTIC)
                ._object(new InputObject().fields(ImmutableMap.of("name", "John")));

        List<EncryptedValue> result = vaultClient.cryptoClient().encrypt(
                EncryptParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .encryptionRequest(ImmutableList.of(request))
                        .build());
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getCiphertext());
    }
}
