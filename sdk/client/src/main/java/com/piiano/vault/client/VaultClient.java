package com.piiano.vault.client;

import com.piiano.vault.client.openapi.ApiClient;
import org.apache.commons.lang3.StringUtils;

/**
 * Client for the Tokens API.
 */
public class VaultClient {

    private final ObjectClient objectClient;
    private final TokenClient tokenClient;
    private final CryptoClient cryptoClient;

    public VaultClient(ApiClient apiClient) {
        this.objectClient = new ObjectClient(apiClient);
        this.tokenClient = new TokenClient(apiClient);
        this.cryptoClient = new CryptoClient(apiClient);
    }

    public ObjectClient objectClient() {
        return objectClient;
    }

    public TokenClient tokenClient() {
        return tokenClient;
    }

    public CryptoClient cryptoClient() {
        return cryptoClient;
    }

    public static ApiClient getPvaultClient() {
        String pvaultUrl = System.getenv("pvault_url");
        if (StringUtils.isEmpty(pvaultUrl)) {
            pvaultUrl = "http://localhost:8123";
        }

        String pvaultAuth = System.getenv("pvault_auth");
        if (StringUtils.isEmpty(pvaultAuth)) {
            pvaultAuth = "pvaultauth";
        }

        ApiClient pvaultClient = com.piiano.vault.client.openapi.Configuration.getDefaultApiClient();
        pvaultClient.setBasePath(pvaultUrl);
        pvaultClient.setBearerToken(pvaultAuth);
        pvaultClient.addDefaultHeader("Content-Type", "application/json");
        return pvaultClient;
    }
}
