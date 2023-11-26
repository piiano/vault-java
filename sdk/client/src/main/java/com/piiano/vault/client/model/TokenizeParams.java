package com.piiano.vault.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Parameters for 'tokenize' API.
 */
@Data
@Builder
public class TokenizeParams {

    /**
     * collection The name of the collection containing the objects. (required)
     */
    private String collection;

    /**
     * expirationSecs Token expiration time in seconds. If not set, the default expiration time is used. See the &#x60;PVAULT_EXPIRATION_TOKENS&#x60; variable. (optional)
     */
    private String expirationSecs;

    /**
     * accessReason Details of the reason for requesting the property. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false. (required)
     */
     private AccessReason accessReason;

    /**
     * reloadCache Reloads the cache before the action. (optional)
     */
    private Boolean reloadCache;

    /**
     * xTenantId List of tenant IDs to enforce on the request.
     */
    private List<String> xTenantId;

    /**
     * transactionId The transaction ID to attach to the token.
     */
    private String transactionId;
}
