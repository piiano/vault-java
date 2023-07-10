package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import org.apache.commons.lang3.StringUtils;

/**
 * Client for the Tokens API.
 */
public class VaultClient {

    private final TokensClient tokensClient;
    private final CryptoClient cryptoClient;

    public VaultClient(ApiClient apiClient, DefaultParams defaultParams) {
        this.tokensClient = new TokensClient(apiClient, defaultParams);
        this.cryptoClient = new CryptoClient(apiClient, defaultParams);
    }

    public TokensClient tokensClient() {
        return tokensClient;
    }

    public CryptoClient cryptoClint() {
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