package com.piiano.vault.client;

import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DefaultParams;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.InputObject;
import com.piiano.vault.client.openapi.model.TokenType;
import com.piiano.vault.client.openapi.model.TokenValue;
import com.piiano.vault.client.openapi.model.TokenizeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.piiano.vault.client.CollectionSetup.collectionName;
import static com.piiano.vault.client.DefaultClient.getDefaultClient;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokensClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final TokensClient tokensClient = new TokensClient(apiClient,
            DefaultParams.builder().collection(collectionName).accessReason(AccessReason.AppFunctionality).build());

    @BeforeEach
    public void beforeEach() throws ApiException {
        CollectionSetup.setUp();
    }

    @AfterEach
    public void afterEach() {
        CollectionSetup.tearDown();
    }

    @Test
    public void tokenizeTest() throws ApiException {
        TokenizeRequest request = new TokenizeRequest()
                .type(TokenType.DETERMINISTIC)
                ._object(new InputObject().fields(ImmutableMap.of("name", "John")));

        TokenValue result = tokensClient.tokenize(request);
        assertNotNull(result.getTokenId());
    }
}
