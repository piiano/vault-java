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

public class ObjectClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final ObjectClient objectClient = new ObjectClient(apiClient);

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

        ObjectID objectID = objectClient.addObject(
                AddObjectParams.builder()
                        .collection(collectionName)
                        .fields(fields)
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertNotNull(objectID);
        assertNotNull(objectID.getId());

        // Get the object
        Map<String, Object> objectFields = objectClient.getObject(
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
        objectClient.updateObject(
                UpdateObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .fields(ImmutableMap.of(propName, newName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        // get the object again and validate the updated value
        objectFields = objectClient.getObject(
                GetObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertEquals(1, objectFields.size());
        assertEquals(newName, objectFields.get(propName));

        // Search the object
        ObjectFieldsPage objectsPage = objectClient.searchObjects(
                SearchObjectsParams.builder()
                        .collection(collectionName)
                        .query(new Query().match(ImmutableMap.of("id", objectID.getId())))
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());
        assertEquals(1, objectsPage.getResults().size());
        assertEquals(newName, objectsPage.getResults().get(0).get(propName));

        objectClient.deleteObject(
                DeleteObjectParams.builder()
                        .collection(collectionName)
                        .objectId(objectID.getId())
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        // Search the token again and verify it is deleted
        objectsPage = objectClient.searchObjects(
                SearchObjectsParams.builder()
                        .collection(collectionName)
                        .query(new Query().match(ImmutableMap.of("id", objectID.getId())))
                        .props(ImmutableList.of(propName))
                        .accessReason(AccessReason.AppFunctionality)
                        .build());

        assertEquals(0, objectsPage.getResults().size());
    }
}
