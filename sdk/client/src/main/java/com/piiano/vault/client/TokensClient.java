package com.piiano.vault.client;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.TokensApi;
import com.piiano.vault.client.openapi.TokensApi.APIdeleteTokensRequest;
import com.piiano.vault.client.openapi.TokensApi.APIdetokenizeRequest;
import com.piiano.vault.client.openapi.TokensApi.APIrotateTokensRequest;
import com.piiano.vault.client.openapi.TokensApi.APIsearchTokensRequest;
import com.piiano.vault.client.openapi.TokensApi.APItokenizeRequest;
import com.piiano.vault.client.openapi.TokensApi.APIupdateTokensRequest;
import com.piiano.vault.client.openapi.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Client for the Tokens API.
 */
public class TokensClient {

    private final TokensApi tokensApi;

    private final DefaultParams defaultParams;

    public TokensClient(ApiClient apiClient, DefaultParams defaultParams) {
        this.tokensApi = new TokensApi(apiClient);
        this.defaultParams = defaultParams;
    }

    public TokensApi openapi() {
        return tokensApi;
    }

    /**
     * Tokenize
     * Creates a token that reference the values of an object's properties. The
     * token ID is partially or wholly randomly-generated and, therefore, is not
     * sensitive. Supports bulk operations. The returned token IDs are in the same
     * order as the object IDs in the request. No tokens are created if any object
     * IDs are invalid or not found. If this operation is called for an object ID
     * and properties that have a token: - Any token tags are appended to the
     * existing token. - If an expiration is specified, then the token expiry is
     * updated. If an expiration is not specified, the token expiry is updated if
     * the default settings result in an expiry date after the token's current
     * expiry date. The role performing this operation must have both of these: -
     * The `CapTokensWriter` capability. - At least one allowing policy and no
     * denying policies for the `tokenize` operation for each of the collection
     * properties specified in the call. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenizeRequest Details of the tokenization request. (required)
     * @return TokenValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code {@code @http.response.details}}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, objects, or properties aren't found
     *                      or are missing.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public TokenValue tokenize(TokenizeRequest tokenizeRequest) throws ApiException {

        List<TokenValue> tokenValues = this.tokensApi.tokenize(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                ImmutableList.of(tokenizeRequest)).execute();

        if (tokenValues.size() == 0) {
            return null;
        }
        return tokenValues.get(0);
    }

    /**
     * Tokenize
     * Creates a token that reference the values of an object's properties. The
     * token ID is partially or wholly randomly-generated and, therefore, is not
     * sensitive. Supports bulk operations. The returned token IDs are in the same
     * order as the object IDs in the request. No tokens are created if any object
     * IDs are invalid or not found. If this operation is called for an object ID
     * and properties that have a token: - Any token tags are appended to the
     * existing token. - If an expiration is specified, then the token expiry is
     * updated. If an expiration is not specified, the token expiry is updated if
     * the default settings result in an expiry date after the token's current
     * expiry date. The role performing this operation must have both of these: -
     * The `CapTokensWriter` capability. - At least one allowing policy and no
     * denying policies for the `tokenize` operation for each of the collection
     * properties specified in the call. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenizeRequest Details of the tokenization request. (required)
     * @param tokenizeParams  Additional params for the request.
     * @return TokenValue
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, objects, or properties aren't found
     *                      or are missing.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public TokenValue tokenize(TokenizeRequest tokenizeRequest, TokenizeParams tokenizeParams) throws ApiException {

        AccessReason accessReason = tokenizeParams.getAccessReason() != null ? tokenizeParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APItokenizeRequest atr = this.tokensApi.tokenize(
                !Strings.isNullOrEmpty(tokenizeParams.getCollection()) ? tokenizeParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason(),
                ImmutableList.of(tokenizeRequest));
        setTokenizeCommonParams(tokenizeParams, atr);

        List<TokenValue> tokenValues = atr.execute();

        if (tokenValues.size() == 0) {
            return null;
        }
        return tokenValues.get(0);
    }

    private void setTokenizeCommonParams(TokenizeParams tokenizeParams, APItokenizeRequest atr) {
        atr.expirationSecs(
                !Strings.isNullOrEmpty(tokenizeParams.getExpirationSecs()) ? tokenizeParams.getExpirationSecs()
                        : this.defaultParams.getExpirationSecs());
        atr.xTenantId(tokenizeParams.getXTenantId());
        atr.transactionId(!Strings.isNullOrEmpty(tokenizeParams.getTransactionId()) ? tokenizeParams.getTransactionId()
                : this.defaultParams.getTransactionId());
        atr.reloadCache(tokenizeParams.getReloadCache() != null ? tokenizeParams.getReloadCache()
                : this.defaultParams.isReloadCache());
        if (tokenizeParams.getAccessReason() == AccessReason.Other) {
            atr.adhocReason(tokenizeParams.getAccessReason().getAdhocReason());
        }
    }

    /**
     * Tokenize bulk
     * Creates a token that reference the values of an object's properties. The
     * token ID is partially or wholly randomly-generated and, therefore, is not
     * sensitive. Supports bulk operations. The returned token IDs are in the same
     * order as the object IDs in the request. No tokens are created if any object
     * IDs are invalid or not found. If this operation is called for an object ID
     * and properties that have a token: - Any token tags are appended to the
     * existing token. - If an expiration is specified, then the token expiry is
     * updated. If an expiration is not specified, the token expiry is updated if
     * the default settings result in an expiry date after the token's current
     * expiry date. The role performing this operation must have both of these: -
     * The `CapTokensWriter` capability. - At least one allowing policy and no
     * denying policies for the `tokenize` operation for each of the collection
     * properties specified in the call. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenizeRequests Details of the tokenization requests. (required)
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, objects, or properties aren't found
     *                      or are missing.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<TokenValue> tokenizeBulk(List<TokenizeRequest> tokenizeRequests) throws ApiException {

        APItokenizeRequest atr = this.tokensApi.tokenize(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                tokenizeRequests);

        atr.expirationSecs(this.defaultParams.getExpirationSecs())
                .xTenantId(this.defaultParams.getXTenantId())
                .transactionId(this.defaultParams.getTransactionId())
                .reloadCache(this.defaultParams.isReloadCache());

        return atr.execute();
    }

    /**
     * Tokenize bulk
     * Creates a token that reference the values of an object's properties. The
     * token ID is partially or wholly randomly-generated and, therefore, is not
     * sensitive. Supports bulk operations. The returned token IDs are in the same
     * order as the object IDs in the request. No tokens are created if any object
     * IDs are invalid or not found. If this operation is called for an object ID
     * and properties that have a token: - Any token tags are appended to the
     * existing token. - If an expiration is specified, then the token expiry is
     * updated. If an expiration is not specified, the token expiry is updated if
     * the default settings result in an expiry date after the token's current
     * expiry date. The role performing this operation must have both of these: -
     * The `CapTokensWriter` capability. - At least one allowing policy and no
     * denying policies for the `tokenize` operation for each of the collection
     * properties specified in the call. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenizeRequests Details of the tokenization requests. (required)
     * @param tokenizeParams   Additional params for the request.
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, objects, or properties aren't found
     *                      or are missing.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<TokenValue> tokenizeBulk(List<TokenizeRequest> tokenizeRequests, TokenizeParams tokenizeParams)
            throws ApiException {

        APItokenizeRequest atr = this.tokensApi.tokenize(
                !Strings.isNullOrEmpty(tokenizeParams.getCollection()) ? tokenizeParams.getCollection()
                        : this.defaultParams.getCollection(),
                tokenizeParams.getAccessReason() != null ? tokenizeParams.getAccessReason().getReason()
                        : this.defaultParams.getAccessReason().getReason(),
                tokenizeRequests);

        setTokenizeCommonParams(tokenizeParams, atr);
        return atr.execute();
    }

    private void setDetokenizeCommonParams(
            APIdetokenizeRequest adr,
            @Nullable DetokenizeParams dParams,
            QueryToken queryToken,
            @Nullable List<String> props,
            Set<String> options) {

        List<String> tenantIdList = this.defaultParams.getXTenantId();
        if ((dParams != null) && dParams.getXTenantId() != null) {
            tenantIdList = dParams.getXTenantId();
        }

        boolean reloadCache = this.defaultParams.isReloadCache();
        if ((dParams != null) && dParams.getReloadCache() != null) {
            reloadCache = dParams.getReloadCache();
        }

        if ((dParams != null) && dParams.getAccessReason() == AccessReason.Other) {
            adr.adhocReason(dParams.getAccessReason().getAdhocReason());
        }

        if (props != null) {
            adr.props(props);
        }
        adr.objectIds(queryToken.getObjectIds())
                .tags(queryToken.getTags())
                .tokenIds(queryToken.getTokenIds())
                .options(options)
                .xTenantId(tenantIdList)
                .reloadCache(reloadCache);
    }

    /**
     * Detokenize tokens
     * Returns the object property values for tokens. The tokens returned by this
     * operation are defined using three query parameters: `token_ids`,
     * `object_ids`, and `tags`. The operation returns an empty response if it finds
     * no matches. See the [Retrieve a
     * token](/guides/tokenize-personal-data/retrieve-a-token) guide for more
     * information about how to match tokens for this operation. The role performing
     * this operation must have all of these: - The `CapTokensDetokenizer`
     * capability. - Policies: + At least one allowing policy and no denying
     * policies for the `detokenize` operation for each of the collection properties
     * that are tokenized by tokens specified in the query. + At least one allowing
     * policy and no denying policies for the `read` operation for each of the
     * collection properties that are tokenized by tokens specified in the query.
     * See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken token_ids, object_ids, and tags to query the token.
     * @param props      The list of property names and transformers. To include
     *                   multiple names and transformations, provide a
     *                   comma-separated list. For example,
     *                   `props&#x3D;first_name,last_name,email.mask`. If not set,
     *                   return all property values. (optional)
     * @param options    Options for the operation. Options include: -
     *                   `include_metadata` - show token metadata in the response.
     *                   (optional)
     * @return List<DetokenizedToken>;
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The requested resource is not found.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<DetokenizedToken> detokenize(QueryToken queryToken, List<String> props, Set<String> options)
            throws ApiException {

        APIdetokenizeRequest adr = this.tokensApi.detokenize(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason());

        setDetokenizeCommonParams(adr, null, queryToken, props, options);
        return adr.execute();
    }

    /**
     * Detokenize tokens
     * Returns the object property values for tokens. The tokens returned by this
     * operation are defined using three query parameters: `token_ids`,
     * `object_ids`, and `tags`. The operation returns an empty response if it finds
     * no matches. See the [Retrieve a
     * token](/guides/tokenize-personal-data/retrieve-a-token) guide for more
     * information about how to match tokens for this operation. The role performing
     * this operation must have all of these: - The `CapTokensDetokenizer`
     * capability. - Policies: + At least one allowing policy and no denying
     * policies for the `detokenize` operation for each of the collection properties
     * that are tokenized by tokens specified in the query. + At least one allowing
     * policy and no denying policies for the `read` operation for each of the
     * collection properties that are tokenized by tokens specified in the query.
     * See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken       token_ids, object_ids, and tags to query the token.
     * @param props            The list of property names and transformers. To
     *                         include multiple names and transformations, provide a
     *                         comma-separated list. For example,
     *                         `props=first_name,last_name,email.mask`. If not set,
     *                         return all property values. (optional)
     * @param options          Options for the operation. Options include: -
     *                         `include_metadata` - show token metadata in the
     *                         response. (optional)
     * @param detokenizeParams Additional params for the request.
     * @return List<DetokenizedToken>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The requested resource is not found.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<DetokenizedToken> detokenize(QueryToken queryToken, List<String> props, Set<String> options,
            DetokenizeParams detokenizeParams) throws ApiException {

        AccessReason accessReason = detokenizeParams.getAccessReason() != null ? detokenizeParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APIdetokenizeRequest adr = this.tokensApi.detokenize(
                !Strings.isNullOrEmpty(detokenizeParams.getCollection()) ? detokenizeParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason());

        setDetokenizeCommonParams(adr, detokenizeParams, queryToken, props, options);

        return adr.execute();
    }

    /**
     * Search tokens
     * Lists tokens with their metadata. The tokens returned by this operation are
     * defined using three query parameters: `token_ids`, `object_ids`, and `tags`.
     * The operation returns an empty response if it finds no matches. See the
     * [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for
     * more information about how to match tokens for this operation. The role
     * performing this operation must have all of these: - The `CapTokensReader`
     * capability. - At least one allowing policy and no denying policies for the
     * `read` operation for the `tokens` resource of the specified collection. See
     * [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken token_ids, object_ids, and tags to query the token.
     *                   (required)
     * @param options    Options for the operation. Options include: - `archived` –
     *                   whether to search only archived tokens. If not specified,
     *                   search only active tokens. (optional)
     * @return List<TokenMetadata>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The requested resource is not found.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<TokenMetadata> searchTokens(QueryToken queryToken, Set<String> options) throws ApiException {

        APIsearchTokensRequest astr = this.tokensApi.searchTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                queryToken);

        astr.xTenantId(this.defaultParams.getXTenantId())
                .reloadCache(this.defaultParams.isReloadCache());
        return astr.execute();
    }

    /**
     * Search tokens
     * Lists tokens with their metadata. The tokens returned by this operation are
     * defined using three query parameters: `token_ids`, `object_ids`, and `tags`.
     * The operation returns an empty response if it finds no matches. See the
     * [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for
     * more information about how to match tokens for this operation. The role
     * performing this operation must have all of these: - The `CapTokensReader`
     * capability. - At least one allowing policy and no denying policies for the
     * `read` operation for the `tokens` resource of the specified collection. See
     * [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken         The token query. (required)
     * @param options            Options for the operation. Options include: -
     *                           `archived` – whether to search only archived
     *                           tokens. If not specified, search only active
     *                           tokens. (optional)
     * @param searchTokensParams Additional params for the request.
     * @return List<TokenMetadata>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The requested resource is not found.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public List<TokenMetadata> searchTokens(QueryToken queryToken, Set<String> options,
            SearchTokensParams searchTokensParams) throws ApiException {

        AccessReason accessReason = searchTokensParams.getAccessReason() != null ? searchTokensParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APIsearchTokensRequest astr = this.tokensApi.searchTokens(
                !Strings.isNullOrEmpty(searchTokensParams.getCollection()) ? searchTokensParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason(),
                queryToken);

        if (accessReason == AccessReason.Other) {
            astr.adhocReason(accessReason.getAdhocReason());
        }

        astr.options(options)
                .xTenantId(searchTokensParams.getXTenantId())
                .reloadCache(searchTokensParams.getReloadCache() != null ? searchTokensParams.getReloadCache()
                        : this.defaultParams.isReloadCache());

        return astr.execute();
    }

    /**
     * Update tokens
     * Updates `tags` and `expiration` token metadata. The role performing this
     * operation must have both of these: - The `CapTokensWriter` capability. - At
     * least one allowing policy and no denying policies for the `write` operation
     * for the `tokens` resource of the collection specified in the call. See
     * [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data. The tokens returned by this
     * operation are defined using three query parameters: `token_ids`,
     * `object_ids`, and `tags`. If no tokens are matched, status code 404 is
     * returned. See the [Retrieve a
     * token](/guides/tokenize-personal-data/retrieve-a-token) guide for more
     * information about how to match tokens for this operation.
     * 
     * @param updateTokenRequest Update token request details. (required)
     * @param queryToken         token_ids, object_ids, and tags to query the token.
     *                           (required)
     * @param options            Options for the operation. Options include: -
     *                           `archived` – whether to update only archived
     *                           tokens. If not specified, update only active
     *                           tokens. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection or reason aren't found or are
     *                      missing, the `reason` is set to `other` but no
     *                      `adhoc_reason` is provided, no token query parameters
     *                      are provided, or the query finds no matching tokens.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public void updateTokens(UpdateTokenRequest updateTokenRequest, QueryToken queryToken, Set<String> options)
            throws ApiException {

        APIupdateTokensRequest autr = this.tokensApi.updateTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                updateTokenRequest);

        autr.expirationSecs(this.defaultParams.getExpirationSecs())
                .objectIds(queryToken.getObjectIds())
                .tags(queryToken.getTags())
                .tokenIds(queryToken.getTokenIds())
                .options(options)
                .xTenantId(this.defaultParams.getXTenantId())
                .reloadCache(this.defaultParams.isReloadCache());

        autr.execute(); /** TODO - why is this void? */
    }

    /**
     * Update tokens
     * Updates `tags` and `expiration` token metadata. The role performing this
     * operation must have both of these: - The `CapTokensWriter` capability. - At
     * least one allowing policy and no denying policies for the `write` operation
     * for the `tokens` resource of the collection specified in the call. See
     * [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data. The tokens returned by this
     * operation are defined using three query parameters: `token_ids`,
     * `object_ids`, and `tags`. If no tokens are matched, status code 404 is
     * returned. See the [Retrieve a
     * token](/guides/tokenize-personal-data/retrieve-a-token) guide for more
     * information about how to match tokens for this operation.
     * 
     * @param updateTokenRequest Update token request details. (required)
     * @param queryToken         token_ids, object_ids, and tags to query the token.
     *                           (required)
     * @param options            Options for the operation. Options include: -
     *                           `archived` – whether to update only archived
     *                           tokens. If not specified, update only active
     *                           tokens. (optional)
     * @param updateTokensParams Additional params for the request.
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code {@code @http.response.details}}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection or reason aren't found or are
     *                      missing, the `reason` is set to `other` but no
     *                      `adhoc_reason` is provided, no token query parameters
     *                      are provided, or the query finds no matching tokens.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public void updateTokens(UpdateTokenRequest updateTokenRequest, QueryToken queryToken, Set<String> options,
            UpdateTokensParams updateTokensParams) throws ApiException {

        AccessReason accessReason = updateTokensParams.getAccessReason() != null ? updateTokensParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APIupdateTokensRequest autr = this.tokensApi.updateTokens(
                !Strings.isNullOrEmpty(updateTokensParams.getCollection()) ? updateTokensParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason(),
                updateTokenRequest);

        autr.expirationSecs(updateTokensParams.getExpirationSecs())
                .objectIds(queryToken.getObjectIds())
                .tags(queryToken.getTags())
                .tokenIds(queryToken.getTokenIds())
                .options(options)
                .xTenantId(updateTokensParams.getXTenantId());

        if (accessReason == AccessReason.Other) {
            autr.adhocReason(accessReason.getAdhocReason());
        }

        autr.reloadCache(updateTokensParams.getReloadCache() != null ? updateTokensParams.getReloadCache()
                : this.defaultParams.isReloadCache());
    }

    /**
     * Delete tokens
     * Deletes tokens. The tokens to delete are those that match all the criteria in
     * the `token_ids`, `object_ids`, and `tags` parameters. If the token query
     * finds no matches, the operation returns a 404 error. tenant ID filter is
     * null, can be set in DeleteTokensParams for the other method . See [search
     * tokens](search-tokens) for more details. The role performing this operation
     * must have both of these: - The `CapTokensWriter` capability. - At least one
     * allowing policy and no denying policies for the `delete` operation for the
     * `tokens` resource of the specified collection. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken token_ids, object_ids, and tags to query the token.
     *                   (required)
     * @param options    Options for the operation. Options include: - `archived` –
     *                   whether to delete only archived tokens. If not specified,
     *                   delete only active tokens. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection or reason aren't found or are
     *                      missing, the `reason` is set to `other` but no
     *                      `adhoc_reason` is provided, no token query parameters
     *                      are provided, or the query finds no matching tokens.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public void deleteTokens(QueryToken queryToken, Set<String> options) throws ApiException {

        APIdeleteTokensRequest adtr = this.tokensApi.deleteTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason());

        adtr.objectIds(queryToken.getObjectIds())
                .tags(queryToken.getTags())
                .tokenIds(queryToken.getTokenIds())
                .options(options)
                .xTenantId(this.defaultParams.getXTenantId())
                .reloadCache(this.defaultParams.isReloadCache());

        adtr.execute();
    }

    /**
     * Delete tokens
     * Deletes tokens. The tokens to delete are those that match all the criteria in
     * the `token_ids`, `object_ids`, and `tags` parameters. If the token query
     * finds no matches, the operation returns a 404 error. See [search
     * tokens](search-tokens) for more details. The role performing this operation
     * must have both of these: - The `CapTokensWriter` capability. - At least one
     * allowing policy and no denying policies for the `delete` operation for the
     * `tokens` resource of the specified collection. See [identity and access
     * management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param queryToken         token_ids, object_ids, and tags to query the token.
     *                           (required)
     * @param options            Options for the operation. Options include: -
     *                           `archived` – whether to delete only archived
     *                           tokens. If not specified, delete only active
     *                           tokens. (optional)
     * @param deleteTokensParams Additional params for the request.
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code {@code @http.response.details}}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The requested resource is not found.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public void deleteTokens(QueryToken queryToken, Set<String> options, DeleteTokensParams deleteTokensParams)
            throws ApiException {

        AccessReason accessReason = deleteTokensParams.getAccessReason() != null ? deleteTokensParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APIdeleteTokensRequest adtr = this.tokensApi.deleteTokens(
                !Strings.isNullOrEmpty(deleteTokensParams.getCollection()) ? deleteTokensParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason());
        adtr.objectIds(queryToken.getObjectIds())
                .tags(queryToken.getTags())
                .tokenIds(queryToken.getTokenIds())
                .options(options)
                .xTenantId(deleteTokensParams.getXTenantId())
                .reloadCache(deleteTokensParams.getReloadCache() != null ? deleteTokensParams.getReloadCache()
                        : this.defaultParams.isReloadCache());

        adtr.execute();
    }

    /**
     * Rotate tokens
     * Generates new token IDs for a list of tokens. The role performing this
     * operation must have both of these: - The `CapTokensWriter` capability. - At
     * least one allowing policy and no denying policies for the `write` operation
     * for the `tokens` resource of the specified collection. See [identity and
     * access management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenIds Comma-separated list of token IDs. (required)
     * @return Map<String, String>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, reason, or tokens aren't found or
     *                      are missing or the `reason` is set to `other` but no
     *                      `adhoc_reason` is provided.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public Map<String, String> rotateTokens(List<String> tokenIds) throws ApiException {

        APIrotateTokensRequest artr = this.tokensApi.rotateTokens(
                tokenIds,
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason());

        artr.xTenantId(this.defaultParams.getXTenantId())
                .reloadCache(this.defaultParams.isReloadCache());

        return artr.execute();
    }

    /**
     * Rotate tokens
     * Generates new token IDs for a list of tokens. The role performing this
     * operation must have both of these: - The `CapTokensWriter` capability. - At
     * least one allowing policy and no denying policies for the `write` operation
     * for the `tokens` resource of the specified collection. See [identity and
     * access management](/data-security/identity-and-access-management) for more
     * information about how capabilities are used to control access to operations
     * and policies are used to control access to data.
     * 
     * @param tokenIds           Comma-separated list of token IDs. (required)
     * @param rotateTokensParams Additional params for the request.
     * @return Map<String, String>
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     *                      {@code @http.response.details}
     *                      <table summary="Response Details" border="1">
     *                      <tr>
     *                      <td>Status Code</td>
     *                      <td>Description</td>
     *                      <td>Response Headers</td>
     *                      </tr>
     *                      <tr>
     *                      <td>200</td>
     *                      <td>The request is successful.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>400</td>
     *                      <td>The request is invalid.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>401</td>
     *                      <td>Authentication credentials are incorrect or missing.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>403</td>
     *                      <td>The caller doesn't have the required access rights.
     *                      </td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>404</td>
     *                      <td>The collection, reason, or tokens aren't found or
     *                      are missing or the `reason` is set to `other` but no
     *                      `adhoc_reason` is provided.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>409</td>
     *                      <td>A conflict occurs.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>500</td>
     *                      <td>An error occurs on the server.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      <tr>
     *                      <td>503</td>
     *                      <td>The service is unavailable.</td>
     *                      <td>-</td>
     *                      </tr>
     *                      </table>
     */
    public Map<String, String> rotateTokens(List<String> tokenIds, RotateTokensParams rotateTokensParams)
            throws ApiException {

        AccessReason accessReason = rotateTokensParams.getAccessReason() != null ? rotateTokensParams.getAccessReason()
                : this.defaultParams.getAccessReason();

        APIrotateTokensRequest artr = this.tokensApi.rotateTokens(
                tokenIds,
                !Strings.isNullOrEmpty(rotateTokensParams.getCollection()) ? rotateTokensParams.getCollection()
                        : this.defaultParams.getCollection(),
                accessReason.getReason());

        if (accessReason == AccessReason.Other) {
            artr.adhocReason(accessReason.getAdhocReason());
        }

        artr.xTenantId(rotateTokensParams.getXTenantId())
                .reloadCache(rotateTokensParams.getReloadCache() != null ? rotateTokensParams.getReloadCache()
                        : this.defaultParams.isReloadCache());

        return artr.execute();
    }
}
