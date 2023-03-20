package com.piiano.vault.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.DefaultParams;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.model.InputObject;
import com.piiano.vault.client.openapi.model.TokenType;
import com.piiano.vault.client.openapi.model.TokenValue;
import com.piiano.vault.client.openapi.model.TokenizeRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.piiano.vault.client.DefaultClient.getDefaultClient;

public class TokensClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final TokensClient tokensClient = new TokensClient(apiClient,
            DefaultParams.builder().collection("users").accessReason(AccessReason.AppFunctionality).build());

    @Before
    public void init() {
    }

    @Test
    public void tokenizeTest() throws ApiException {
        TokenizeRequest request = new TokenizeRequest()
                .type(TokenType.DETERMINISTIC)
                ._object(new InputObject().fields(ImmutableMap.of("first_name", "John")));

        List<TokenValue> result = tokensClient.tokenize(ImmutableList.of(request));
        Assert.assertNotNull(result.get(0).getTokenId());
    }
}
