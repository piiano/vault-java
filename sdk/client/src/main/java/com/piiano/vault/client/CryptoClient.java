package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.CryptoApi;
import com.piiano.vault.client.openapi.model.*;

import java.util.List;

/**
 * Client for the Crypto API.
 */
public class CryptoClient {

    private final CryptoApi cryptoApi;

    public CryptoClient(ApiClient apiClient) {
        this.cryptoApi = new CryptoApi(apiClient);
    }

    public CryptoApi openapi() {
        return cryptoApi;
    }

    public List<EncryptedValue> encrypt(EncryptParams encryptParams) throws ApiException {

        if (encryptParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.cryptoApi.encrypt(
                encryptParams.getCollection(),
                encryptParams.getAccessReason().getReason(),
                encryptParams.getEncryptionRequest())
                .adhocReason(encryptParams.getAccessReason().getAdhocReason())
                .expirationSecs(encryptParams.getExpirationSecs())
                .customAudit(encryptParams.getCustomAudit())
                .reloadCache(encryptParams.isReloadCache())
                .xTenantId(encryptParams.getXTenantId())
                .execute();
    }

    public List<DecryptedObject> decrypt(DecryptParams decryptParams) throws ApiException {

        if (decryptParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.cryptoApi.decrypt(
            decryptParams.getCollection(),
            decryptParams.getAccessReason().getReason(),
            decryptParams.getDecryptionRequests())
            .adhocReason(decryptParams.getAccessReason().getAdhocReason())
            .options(decryptParams.getOptions())
            .customAudit(decryptParams.getCustomAudit())
            .reloadCache(decryptParams.isReloadCache())
            .xTenantId(decryptParams.getXTenantId())
            .execute();
    }

    public List<EncryptedValue> updateEncrypted(UpdateEncryptedParams updateEncryptedParams) throws ApiException {

        if (updateEncryptedParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.cryptoApi.updateEncrypted(
                updateEncryptedParams.getCollection(),
                updateEncryptedParams.getAccessReason().getReason(),
                updateEncryptedParams.getUpdateEncryptionRequests())
                .adhocReason(updateEncryptedParams.getAccessReason().getAdhocReason())
                .expirationSecs(updateEncryptedParams.getExpirationSecs())
                .options(updateEncryptedParams.getOptions())
                .customAudit(updateEncryptedParams.getCustomAudit())
                .reloadCache(updateEncryptedParams.isReloadCache())
                .xTenantId(updateEncryptedParams.getXTenantId())
                .execute();
    }

    public List<TokenValue> hash(HashParams hashParams) throws ApiException {

        if (hashParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.cryptoApi.hashObjects(
                hashParams.getCollection(),
                hashParams.getAccessReason().getReason(),
                hashParams.getHashObjectRequests())
                .adhocReason(hashParams.getAccessReason().getAdhocReason())
                .customAudit(hashParams.getCustomAudit())
                .reloadCache(hashParams.isReloadCache())
                .execute();
    }
}
