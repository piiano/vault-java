package com.piiano.vault.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.piiano.vault.client.model.AccessReason;
import com.piiano.vault.client.model.EncryptParams;
import com.piiano.vault.client.model.TokenizeParams;
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

public class VaultClientTest {

    private final ApiClient apiClient = getDefaultClient();

    private final VaultClient vaultClient = new VaultClient(apiClient);

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

        List<TokenValue> result = vaultClient.tokenClient().tokenize(
                TokenizeParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .tokenizeRequest(ImmutableList.of(request))
                        .build());
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getTokenId());
    }

    @Test
    public void encryptionTest() throws ApiException {
        EncryptionRequest request = new EncryptionRequest()
                .type(EncryptionType.DETERMINISTIC)
                ._object(new InputObject().fields(ImmutableMap.of("name", "John")));

        List<EncryptedValue> result = vaultClient.cryptoClient().encrypt(
                EncryptParams.builder()
                        .collection(collectionName)
                        .accessReason(AccessReason.AppFunctionality)
                        .encryptionRequest(ImmutableList.of(request))
                        .build());
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getCiphertext());
    }
}
