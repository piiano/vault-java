package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;

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

    public void main(String[] args) {
        
    }
}