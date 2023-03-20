package com.piiano.vault.client;

import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.Configuration;

public class DefaultClient {

    public static ApiClient getDefaultClient() {

        // Create configuration, bearer auth and client API
        ApiClient apiClient = Configuration.getDefaultApiClient();
        apiClient.setBasePath("http://localhost:8123");
        apiClient.setBearerToken("pvaultauth");
        apiClient.addDefaultHeader("Content-Type", "application/json");

        return apiClient;
    }
}
