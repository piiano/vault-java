package com.piiano.vault.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Default parameters for the Vault Clients. These parameters are used by the Vault Client for
 * all requests unless set by the request parameters.
 */
@Data
@Builder
public class DefaultParams {

    /**
     * collection The name of the collection containing the objects. (required)
     */
    private String collection;

    /**
     * accessReason Details of the reason for requesting the property. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false. (required)
     */
    private AccessReason accessReason;

    /**
     * expirationSecs Token expiration time in seconds. If not set, the default expiration time is used. See the &#x60;PVAULT_EXPIRATION_TOKENS&#x60; variable. (optional)
     */
    private String expirationSecs;

    /**
     * reloadCache Reloads the cache before the action. (optional)
     */
    private boolean reloadCache;

    /**
     * xTenantId List of tenant IDs to enforce on the request.
     */
    private List<String> xTenantId;

    /**
     * transactionId The transaction ID to attach to the token.
     */
    private String transactionId;
}
