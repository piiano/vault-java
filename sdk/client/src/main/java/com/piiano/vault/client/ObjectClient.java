package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.ObjectsApi;
import com.piiano.vault.client.openapi.model.*;

import java.util.Map;

/**
 * Client for the Object API.
 */
public class ObjectClient {

    private final ObjectsApi objectsApi;

    public ObjectClient(ApiClient apiClient) {
        this.objectsApi = new ObjectsApi(apiClient);
    }

    public ObjectsApi openapi() {
        return objectsApi;
    }

    public ObjectID addObject(AddObjectParams addObjectParams) throws ApiException {

        if (addObjectParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.addObject(
                addObjectParams.getCollection(),
                addObjectParams.getAccessReason().getReason(),
                addObjectParams.getFields())
                .adhocReason(addObjectParams.getAccessReason().getAdhocReason())
                .expirationSecs(addObjectParams.getExpirationSecs())
                .customAudit(addObjectParams.getCustomAudit())
                .reloadCache(addObjectParams.isReloadCache())
                .xTenantId(addObjectParams.getXTenantId())
                ._import(addObjectParams.getIsImport())
                .exportKey(addObjectParams.getExportKey())
                .execute();
    }

    public BulkObjectResponse addObjects(AddObjectsParams addObjectsParams) throws ApiException {

        if (addObjectsParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.addObjects(
                addObjectsParams.getCollection(),
                addObjectsParams.getAccessReason().getReason(),
                addObjectsParams.getFields())
                .adhocReason(addObjectsParams.getAccessReason().getAdhocReason())
                .expirationSecs(addObjectsParams.getExpirationSecs())
                .customAudit(addObjectsParams.getCustomAudit())
                .reloadCache(addObjectsParams.isReloadCache())
                .xTenantId(addObjectsParams.getXTenantId())
                ._import(addObjectsParams.getIsImport())
                .exportKey(addObjectsParams.getExportKey())
                .execute();
    }

    public void deleteObject(DeleteObjectParams deleteObjectParams) throws ApiException {

        if (deleteObjectParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        this.objectsApi.deleteObjectById(
                deleteObjectParams.getCollection(),
                deleteObjectParams.getObjectId(),
                deleteObjectParams.getAccessReason().getReason())
                .options(deleteObjectParams.getOptions())
                .adhocReason(deleteObjectParams.getAccessReason().getAdhocReason())
                .customAudit(deleteObjectParams.getCustomAudit())
                .reloadCache(deleteObjectParams.isReloadCache())
                .xTenantId(deleteObjectParams.getXTenantId())
                .execute();
    }

    public BulkObjectResponse deleteObjects(DeleteObjectsParams deleteObjectsParams) throws ApiException {

        if (deleteObjectsParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.deleteObjects(
                deleteObjectsParams.getCollection(),
                deleteObjectsParams.getAccessReason().getReason())
                .objectID(deleteObjectsParams.getObjectIds())
                .options(deleteObjectsParams.getOptions())
                .adhocReason(deleteObjectsParams.getAccessReason().getAdhocReason())
                .customAudit(deleteObjectsParams.getCustomAudit())
                .reloadCache(deleteObjectsParams.isReloadCache())
                .xTenantId(deleteObjectsParams.getXTenantId())
                .execute();
    }

    public Map<String, Object> getObject(GetObjectParams getObjectParams) throws ApiException {

        if (getObjectParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.getObjectById(
                getObjectParams.getCollection(),
                getObjectParams.getObjectId(),
                getObjectParams.getAccessReason().getReason())
                .props(getObjectParams.getProps())
                .options(getObjectParams.getOptions())
                .adhocReason(getObjectParams.getAccessReason().getAdhocReason())
                .customAudit(getObjectParams.getCustomAudit())
                .reloadCache(getObjectParams.isReloadCache())
                .xTenantId(getObjectParams.getXTenantId())
                .execute();
    }

    public ObjectFieldsPage listObjects(ListObjectsParams listObjectsParams) throws ApiException {

        if (listObjectsParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.listObjects(
                listObjectsParams.getCollection(),
                listObjectsParams.getAccessReason().getReason())
                .adhocReason(listObjectsParams.getAccessReason().getAdhocReason())
                .ids(listObjectsParams.getObjectIds())
                .props(listObjectsParams.getProps())
                .cursor(listObjectsParams.getCursor())
                .pageSize(listObjectsParams.getPageSize())
                .options(listObjectsParams.getOptions())
                .customAudit(listObjectsParams.getCustomAudit())
                .reloadCache(listObjectsParams.isReloadCache())
                .xTenantId(listObjectsParams.getXTenantId())
                .xTransParam(listObjectsParams.getXTransParam())
                .execute();
    }

    public ObjectFieldsPage searchObjects(SearchObjectsParams searchObjectsParams) throws ApiException {

        if (searchObjectsParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.searchObjects(
                searchObjectsParams.getCollection(),
                searchObjectsParams.getAccessReason().getReason(),
                searchObjectsParams.getQuery())
                .adhocReason(searchObjectsParams.getAccessReason().getAdhocReason())
                .props(searchObjectsParams.getProps())
                .cursor(searchObjectsParams.getCursor())
                .pageSize(searchObjectsParams.getPageSize())
                .options(searchObjectsParams.getOptions())
                .customAudit(searchObjectsParams.getCustomAudit())
                .reloadCache(searchObjectsParams.isReloadCache())
                .xTenantId(searchObjectsParams.getXTenantId())
                .xTransParam(searchObjectsParams.getXTransParam())
                .execute();
    }

    public void updateObject(UpdateObjectParams updateObjectParams) throws ApiException {

        if (updateObjectParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        this.objectsApi.updateObjectById(
                updateObjectParams.getCollection(),
                updateObjectParams.getObjectId(),
                updateObjectParams.getAccessReason().getReason(),
                updateObjectParams.getFields())
                .adhocReason(updateObjectParams.getAccessReason().getAdhocReason())
                .expirationSecs(updateObjectParams.getExpirationSecs())
                .options(updateObjectParams.getOptions())
                .customAudit(updateObjectParams.getCustomAudit())
                .reloadCache(updateObjectParams.isReloadCache())
                .xTenantId(updateObjectParams.getXTenantId())
                ._import(updateObjectParams.getIsImport())
                .exportKey(updateObjectParams.getExportKey())
                .execute();
    }

    public BulkObjectResponse updateObjects(UpdateObjectsParams updateObjectsParams) throws ApiException {

        if (updateObjectsParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.updateObjects(
                updateObjectsParams.getCollection(),
                updateObjectsParams.getAccessReason().getReason())
                .adhocReason(updateObjectsParams.getAccessReason().getAdhocReason())
                .requestBody(updateObjectsParams.getObjects())
                .expirationSecs(updateObjectsParams.getExpirationSecs())
                .options(updateObjectsParams.getOptions())
                .customAudit(updateObjectsParams.getCustomAudit())
                .reloadCache(updateObjectsParams.isReloadCache())
                .xTenantId(updateObjectsParams.getXTenantId())
                ._import(updateObjectsParams.getIsImport())
                .exportKey(updateObjectsParams.getExportKey())
                .execute();
    }

    public Count getObjectsCount(CommonParams commonParams) throws ApiException {

        if (commonParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.objectsApi.getObjectsCount(
                commonParams.getCollection(),
                commonParams.getAccessReason().getReason())
                .adhocReason(commonParams.getAccessReason().getAdhocReason())
                .customAudit(commonParams.getCustomAudit())
                .reloadCache(commonParams.isReloadCache())
                .execute();
    }
}
