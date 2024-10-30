package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.TokensApi;
import com.piiano.vault.client.openapi.model.*;

import java.util.List;
import java.util.Map;

/**
 * Client for the Token API.
 */
public class TokenClient {

    private final TokensApi tokensApi;

    public TokenClient(ApiClient apiClient) {
        this.tokensApi = new TokensApi(apiClient);
    }

    public TokensApi openapi() {
        return tokensApi;
    }

    public List<TokenValue> tokenize(TokenizeParams tokenizeParams) throws ApiException {

        if (tokenizeParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.tokensApi.tokenize(
                tokenizeParams.getCollection(),
                tokenizeParams.getAccessReason().getReason(),
                tokenizeParams.getTokenizeRequest())
                .adhocReason(tokenizeParams.getAccessReason().getAdhocReason())
                .expirationSecs(tokenizeParams.getExpirationSecs())
                .customAudit(tokenizeParams.getCustomAudit())
                .reloadCache(tokenizeParams.isReloadCache())
                .xTenantId(tokenizeParams.getXTenantId())
                .transactionId(tokenizeParams.getTransactionId())
                .execute();
    }

    public List<DetokenizedToken> detokenize(DetokenizeParams detokenizeParams) throws ApiException {

        if (detokenizeParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.tokensApi.detokenize(
                detokenizeParams.getCollection(),
                detokenizeParams.getAccessReason().getReason())
                .adhocReason(detokenizeParams.getAccessReason().getAdhocReason())
                .tokenIds(detokenizeParams.getTokenQuery().getTokenIds())
                .objectIds(detokenizeParams.getTokenQuery().getObjectIds())
                .tags(detokenizeParams.getTokenQuery().getTags())
                .props(detokenizeParams.getProps())
                .options(detokenizeParams.getOptions())
                .customAudit(detokenizeParams.getCustomAudit())
                .reloadCache(detokenizeParams.isReloadCache())
                .xTenantId(detokenizeParams.getXTenantId())
                .execute();
    }

    public List<TokenMetadata> searchTokens(SearchTokensParams searchTokensParams) throws ApiException {

        if (searchTokensParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        QueryToken queryToken = new QueryToken();
        queryToken.setTokenIds(searchTokensParams.getTokenQuery().getTokenIds());
        queryToken.setObjectIds(searchTokensParams.getTokenQuery().getObjectIds());
        queryToken.setTags(searchTokensParams.getTokenQuery().getTags());

        return this.tokensApi.searchTokens(
                searchTokensParams.getCollection(),
                searchTokensParams.getAccessReason().getReason(),
                queryToken)
                .adhocReason(searchTokensParams.getAccessReason().getAdhocReason())
                .options(searchTokensParams.getOptions())
                .customAudit(searchTokensParams.getCustomAudit())
                .reloadCache(searchTokensParams.isReloadCache())
                .xTenantId(searchTokensParams.getXTenantId())
                .execute();
    }

    public void updateTokens(UpdateTokensParams updateTokensParams) throws ApiException {

        if (updateTokensParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        this.tokensApi.updateTokens(
                updateTokensParams.getCollection(),
                updateTokensParams.getAccessReason().getReason(),
                updateTokensParams.getUpdateTokenRequest())
                .adhocReason(updateTokensParams.getAccessReason().getAdhocReason())
                .tokenIds(updateTokensParams.getTokenQuery().getTokenIds())
                .objectIds(updateTokensParams.getTokenQuery().getObjectIds())
                .tags(updateTokensParams.getTokenQuery().getTags())
                .expirationSecs(updateTokensParams.getExpirationSecs())
                .customAudit(updateTokensParams.getCustomAudit())
                .reloadCache(updateTokensParams.isReloadCache())
                .xTenantId(updateTokensParams.getXTenantId())
                .execute();
    }

    public void deleteTokens(DeleteTokensParams deleteTokensParams) throws ApiException {

        if (deleteTokensParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        this.tokensApi.deleteTokens(
                deleteTokensParams.getCollection(),
                deleteTokensParams.getAccessReason().getReason())
                .adhocReason(deleteTokensParams.getAccessReason().getAdhocReason())
                .tokenIds(deleteTokensParams.getTokenQuery().getTokenIds())
                .objectIds(deleteTokensParams.getTokenQuery().getObjectIds())
                .tags(deleteTokensParams.getTokenQuery().getTags())
                .tenantId(deleteTokensParams.getTenantId())
                .options(deleteTokensParams.getOptions())
                .customAudit(deleteTokensParams.getCustomAudit())
                .reloadCache(deleteTokensParams.isReloadCache())
                .xTenantId(deleteTokensParams.getXTenantId())
                .execute();
    }

    public Map<String, String> rotateTokens(RotateTokensParams rotateTokensParams) throws ApiException {

        if (rotateTokensParams.getAccessReason() == null) {
            throw new ApiException("Access reason is required");
        }
        return this.tokensApi.rotateTokens(
                rotateTokensParams.getTokenIds(),
                rotateTokensParams.getCollection(),
                rotateTokensParams.getAccessReason().getReason())
                .adhocReason(rotateTokensParams.getAccessReason().getAdhocReason())
                .customAudit(rotateTokensParams.getCustomAudit())
                .reloadCache(rotateTokensParams.isReloadCache())
                .xTenantId(rotateTokensParams.getXTenantId())
                .execute();
    }
}
