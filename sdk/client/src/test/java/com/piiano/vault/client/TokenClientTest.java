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

import static com.piiano.vault.client.CollectionSetup.collectionName;
import static com.piiano.vault.client.DefaultClient.getDefaultClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final TokenClient tokenClient = new TokenClient(apiClient);

    @BeforeEach
    public void beforeEach() throws ApiException {
        CollectionSetup.setUp();
    }

    @AfterEach
    public void afterEach() {
        CollectionSetup.tearDown();
    }

    @Test
    public void tokenOperationsTest() throws ApiException {

        // Tokenize
        String propName = "name";
        String name = "John";
        TokenizeRequest request = new TokenizeRequest()
                .type(TokenType.RANDOMIZED)
                ._object(new InputObject().fields(ImmutableMap.of(propName, name)));

        List<TokenValue> result = tokenClient.tokenize(
                TokenizeParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenizeRequest(ImmutableList.of(request))
                        .build());
        assertEquals(1, result.size());
        String tokenId = result.get(0).getTokenId();
        assertNotNull(tokenId);

        // Detokenize
        TokenQuery tokenQuery = TokenQuery.builder().tokenIds(ImmutableList.of(tokenId)).build();
        List<DetokenizedToken> detokenized = tokenClient.detokenize(
                DetokenizeParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .build());

        assertEquals(1, detokenized.size());
        assertEquals(name, detokenized.get(0).getFields().get(propName));

        // Update Token
        String newName = "Jane";
        UpdateTokenRequest updateTokenRequest = new UpdateTokenRequest();
        updateTokenRequest._object(new InputObject().fields(ImmutableMap.of(propName, newName)));

        tokenClient.updateTokens(
                UpdateTokensParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .updateTokenRequest(updateTokenRequest)
                        .build());

        // Detokenize again and validate the updated value
        detokenized = tokenClient.detokenize(
                DetokenizeParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .build());

        assertEquals(1, detokenized.size());
        assertEquals(newName, detokenized.get(0).getFields().get(propName));

        // Search the token
        List<TokenMetadata> tokenMetadata = tokenClient.searchTokens(
                SearchTokensParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .build());

        assertEquals(1, tokenMetadata.size());
        assertEquals(TokenType.RANDOMIZED, tokenMetadata.get(0).getType());

        tokenClient.deleteTokens(
                DeleteTokensParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .build());

        // Search the token again and verify it is deleted
        tokenMetadata = tokenClient.searchTokens(
                SearchTokensParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenQuery(tokenQuery)
                        .build());
        assertEquals(0, tokenMetadata.size());
    }
}
