package com.piiano.vault.client;

import com.piiano.vault.client.model.*;
import com.piiano.vault.client.openapi.ApiClient;
import com.piiano.vault.client.openapi.ApiException;
import com.piiano.vault.client.openapi.TokensApi;
import com.piiano.vault.client.openapi.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

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
     * Creates a token that reference the values of an object's properties. The token ID is partially or wholly randomly-generated and, therefore, is not sensitive. Supports bulk operations.  The returned token IDs are in the same order as the object IDs in the request. No tokens are created if any object IDs are invalid or not found.  If this operation is called for an object ID and properties that have a token: - Any token tags are appended to the existing token. - If an expiration is specified, then the token expiry is updated. If an expiration is not specified, the token expiry is updated if the default settings result in an expiry date after the token's current expiry date.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `tokenize` operation for each of the collection properties specified in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param tokenizeRequest Details of the tokenization request. (required)
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenValue> tokenize(List<TokenizeRequest> tokenizeRequest) throws ApiException {

        return this.tokensApi.tokenize(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                tokenizeRequest,
                this.defaultParams.getExpirationSecs(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Tokenize
     * Creates a token that reference the values of an object's properties. The token ID is partially or wholly randomly-generated and, therefore, is not sensitive. Supports bulk operations.  The returned token IDs are in the same order as the object IDs in the request. No tokens are created if any object IDs are invalid or not found.  If this operation is called for an object ID and properties that have a token: - Any token tags are appended to the existing token. - If an expiration is specified, then the token expiry is updated. If an expiration is not specified, the token expiry is updated if the default settings result in an expiry date after the token's current expiry date.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `tokenize` operation for each of the collection properties specified in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param tokenizeRequest Details of the tokenization request. (required)
     * @param tokenizeParams Additional params for the request.
     * @return List<TokenValue>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, objects, or properties aren't found or are missing. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenValue> tokenize(List<TokenizeRequest> tokenizeRequest, TokenizeParams tokenizeParams) throws ApiException {

        AccessReason accessReason = tokenizeParams.getAccessReason() != null ? tokenizeParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.tokensApi.tokenize(
                StringUtils.isNotEmpty(tokenizeParams.getCollection()) ? tokenizeParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                tokenizeRequest,
                tokenizeParams.getExpirationSecs(),
                accessReason.getAdhocReason(),
                tokenizeParams.getReloadCache() != null ? tokenizeParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Detokenize tokens
     * Returns the object property values for tokens.  The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. The operation returns an empty response if it finds no matches. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.  The role performing this operation must have all of these: - The `CapTokensDetokenizer` capability. - Policies:   + At least one allowing policy and no denying policies for the `detokenize` operation for each of the collection properties that are tokenized by tokens specified in the query.   + At least one allowing policy and no denying policies for the `read` operation for each of the collection properties that are tokenized by tokens specified in the query.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken token_ids, object_ids, and tags to query the token.
     * @param props The list of property names and transformers. To include multiple names and transformations, provide a comma-separated list. For example, `props&#x3D;first_name,last_name,email.mask`. If not set, return all property values. (optional)
     * @param options Options for the operation. Options include: - `include_metadata` - show token metadata in the response.  (optional)
     * @return List<DetokenizedToken>;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The requested resource is not found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<DetokenizedToken> detokenize(QueryToken queryToken, List<String> props, List<String> options) throws ApiException {

        return this.tokensApi.detokenize(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                queryToken.getObjectIds(), queryToken.getTags() , queryToken.getTokenIds(),
                props,
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Detokenize tokens
     * Returns the object property values for tokens.  The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. The operation returns an empty response if it finds no matches. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.  The role performing this operation must have all of these: - The `CapTokensDetokenizer` capability. - Policies:   + At least one allowing policy and no denying policies for the `detokenize` operation for each of the collection properties that are tokenized by tokens specified in the query.   + At least one allowing policy and no denying policies for the `read` operation for each of the collection properties that are tokenized by tokens specified in the query.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken token_ids, object_ids, and tags to query the token.
     * @param props The list of property names and transformers. To include multiple names and transformations, provide a comma-separated list. For example, `props=first_name,last_name,email.mask`. If not set, return all property values. (optional)
     * @param options Options for the operation. Options include: - `include_metadata` - show token metadata in the response.  (optional)
     * @param detokenizeParams Additional params for the request.
     * @return List<DetokenizedToken>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The requested resource is not found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<DetokenizedToken> detokenize(QueryToken queryToken, List<String> props, List<String> options, DetokenizeParams detokenizeParams) throws ApiException {

        AccessReason accessReason = detokenizeParams.getAccessReason() != null ? detokenizeParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.tokensApi.detokenize(
                StringUtils.isNotEmpty(detokenizeParams.getCollection()) ? detokenizeParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                queryToken.getObjectIds(), queryToken.getTags() , queryToken.getTokenIds(),
                props,
                options,
                accessReason.getAdhocReason(),
                detokenizeParams.getReloadCache() != null ? detokenizeParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Search tokens
     * Lists tokens with their metadata.   The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. The operation returns an empty response if it finds no matches. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.   The role performing this operation must have all of these: - The `CapTokensReader` capability. - At least one allowing policy and no denying policies for the `read` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken token_ids, object_ids, and tags to query the token. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to search only archived tokens. If not specified, search only active tokens.  (optional)
     * @return List<TokenMetadata>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The requested resource is not found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenMetadata> searchTokens(QueryToken queryToken, List<String> options) throws ApiException {

        return this.tokensApi.searchTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                queryToken,
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Search tokens
     * Lists tokens with their metadata.   The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. The operation returns an empty response if it finds no matches. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.   The role performing this operation must have all of these: - The `CapTokensReader` capability. - At least one allowing policy and no denying policies for the `read` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken The token query. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to search only archived tokens. If not specified, search only active tokens.  (optional)
     * @param searchTokensParams Additional params for the request.
     * @return List<TokenMetadata>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The requested resource is not found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public List<TokenMetadata> searchTokens(QueryToken queryToken, List<String> options, SearchTokensParams searchTokensParams) throws ApiException {

        AccessReason accessReason = searchTokensParams.getAccessReason() != null ? searchTokensParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.tokensApi.searchTokens(
                StringUtils.isNotEmpty(searchTokensParams.getCollection()) ? searchTokensParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                queryToken,
                options,
                accessReason.getAdhocReason(),
                searchTokensParams.getReloadCache() != null ? searchTokensParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Update tokens
     * Updates `tags` and `expiration` token metadata.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `write` operation for the `tokens` resource of the collection specified in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.  The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. If no tokens are matched, status code 404 is returned. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.
     * @param updateTokenRequest Update token request details. (required)
     * @param queryToken token_ids, object_ids, and tags to query the token. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to update only archived tokens. If not specified, update only active tokens.  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> The collection or reason aren't found or are missing, the `reason` is set to `other` but no `adhoc_reason` is provided, no token query parameters are provided, or the query finds no matching tokens. </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
     </table>
     */
    public void updateTokens(UpdateTokenRequest updateTokenRequest, QueryToken queryToken, List<String> options) throws ApiException {

        this.tokensApi.updateTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                updateTokenRequest,
                this.defaultParams.getExpirationSecs(),
                queryToken.getObjectIds(), queryToken.getTags(), queryToken.getTokenIds(),
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Update tokens
     * Updates `tags` and `expiration` token metadata.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `write` operation for the `tokens` resource of the collection specified in the call.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.  The tokens returned by this operation are defined using three query parameters: `token_ids`, `object_ids`, and `tags`. If no tokens are matched, status code 404 is returned. See the [Retrieve a token](/guides/tokenize-personal-data/retrieve-a-token) guide for more information about how to match tokens for this operation.
     * @param updateTokenRequest Update token request details. (required)
     * @param queryToken token_ids, object_ids, and tags to query the token. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to update only archived tokens. If not specified, update only active tokens.  (optional)
     * @param updateTokensParams Additional params for the request.
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> The collection or reason aren't found or are missing, the `reason` is set to `other` but no `adhoc_reason` is provided, no token query parameters are provided, or the query finds no matching tokens. </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
     </table>
     */
    public void updateTokens(UpdateTokenRequest updateTokenRequest, QueryToken queryToken, List<String> options, UpdateTokensParams updateTokensParams) throws ApiException {

        AccessReason accessReason = updateTokensParams.getAccessReason() != null ? updateTokensParams.getAccessReason() : this.defaultParams.getAccessReason();

        this.tokensApi.updateTokens(
                StringUtils.isNotEmpty(updateTokensParams.getCollection()) ? updateTokensParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                updateTokenRequest,
                updateTokensParams.getExpirationSecs(),
                queryToken.getObjectIds(), queryToken.getTags(), queryToken.getTokenIds(),
                options,
                accessReason.getAdhocReason(),
                updateTokensParams.getReloadCache() != null ? updateTokensParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Delete tokens
     * Deletes tokens.  The tokens to delete are those that match all the criteria in the `token_ids`, `object_ids`,  and `tags` parameters. If the token query finds no matches, the operation returns a 404 error. See [search tokens](search-tokens) for more details.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `delete` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken token_ids, object_ids, and tags to query the token. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to delete only archived tokens. If not specified, delete only active tokens.  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection or reason aren't found or are missing, the `reason` is set to `other` but no `adhoc_reason` is provided, no token query parameters are provided, or the query finds no matching tokens. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public void deleteTokens(QueryToken queryToken, List<String> options) throws ApiException {

        this.tokensApi.deleteTokens(
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                queryToken.getObjectIds(), queryToken.getTags() , queryToken.getTokenIds(),
                options,
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Delete tokens
     * Deletes tokens.  The tokens to delete are those that match all the criteria in the `token_ids`, `object_ids`,  and `tags` parameters. If the token query finds no matches, the operation returns a 404 error. See [search tokens](search-tokens) for more details.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `delete` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param queryToken token_ids, object_ids, and tags to query the token. (required)
     * @param options Options for the operation. Options include: - `archived` – whether to delete only archived tokens. If not specified, delete only active tokens.  (optional)
     * @param deleteTokensParams Additional params for the request.
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The requested resource is not found. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public void deleteTokens(QueryToken queryToken, List<String> options, DeleteTokensParams deleteTokensParams) throws ApiException {

        AccessReason accessReason = deleteTokensParams.getAccessReason() != null ? deleteTokensParams.getAccessReason() : this.defaultParams.getAccessReason();

        this.tokensApi.deleteTokens(
                StringUtils.isNotEmpty(deleteTokensParams.getCollection()) ? deleteTokensParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                queryToken.getObjectIds(), queryToken.getTags() , queryToken.getTokenIds(),
                options,
                accessReason.getAdhocReason(),
                deleteTokensParams.getReloadCache() != null ? deleteTokensParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }

    /**
     * Rotate tokens
     * Generates new token IDs for a list of tokens.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `write` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param tokenIds Comma-separated list of token IDs. (required)
     * @return Map<String, String>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, reason, or tokens aren't found or are missing or the `reason` is set to `other` but no `adhoc_reason` is provided. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public Map<String, String> rotateTokens(List<String> tokenIds) throws ApiException {

        return this.tokensApi.rotateTokens(
                tokenIds,
                this.defaultParams.getCollection(),
                this.defaultParams.getAccessReason().getReason(),
                this.defaultParams.getAccessReason().getAdhocReason(),
                this.defaultParams.isReloadCache()
        );
    }

    /**
     * Rotate tokens
     * Generates new token IDs for a list of tokens.  The role performing this operation must have both of these: - The `CapTokensWriter` capability. - At least one allowing policy and no denying policies for the `write` operation for the `tokens` resource of the specified collection.  See [identity and access management](/data-security/identity-and-access-management) for more information about how capabilities are used to control access to operations and policies are used to control access to data.
     * @param tokenIds Comma-separated list of token IDs. (required)
     * @param rotateTokensParams Additional params for the request.
     * @return Map<String, String>
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
    <table summary="Response Details" border="1">
    <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
    <tr><td> 200 </td><td> The request is successful. </td><td>  -  </td></tr>
    <tr><td> 400 </td><td> The request is invalid. </td><td>  -  </td></tr>
    <tr><td> 401 </td><td> Authentication credentials are incorrect or missing. </td><td>  -  </td></tr>
    <tr><td> 403 </td><td> The caller doesn't have the required access rights. </td><td>  -  </td></tr>
    <tr><td> 404 </td><td> The collection, reason, or tokens aren't found or are missing or the `reason` is set to `other` but no `adhoc_reason` is provided. </td><td>  -  </td></tr>
    <tr><td> 409 </td><td> A conflict occurs. </td><td>  -  </td></tr>
    <tr><td> 500 </td><td> An error occurs on the server. </td><td>  -  </td></tr>
    <tr><td> 503 </td><td> The service is unavailable. </td><td>  -  </td></tr>
    </table>
     */
    public Map<String, String> rotateTokens(List<String> tokenIds, RotateTokensParams rotateTokensParams) throws ApiException {

        AccessReason accessReason = rotateTokensParams.getAccessReason() != null ? rotateTokensParams.getAccessReason() : this.defaultParams.getAccessReason();

        return this.tokensApi.rotateTokens(
                tokenIds,
                StringUtils.isNotEmpty(rotateTokensParams.getCollection()) ? rotateTokensParams.getCollection() : this.defaultParams.getCollection(),
                accessReason.getReason(),
                accessReason.getAdhocReason(),
                rotateTokensParams.getReloadCache() != null ? rotateTokensParams.getReloadCache() : this.defaultParams.isReloadCache()
        );
    }
}
