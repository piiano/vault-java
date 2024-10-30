package com.piiano.vault.client.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Common parameters for the Vault Clients.
 */
@Data
@SuperBuilder
public class CommonParams {

    /**
     * collection The name of the collection containing the objects.
     */
    private String collection;

    /**
     * accessReason Details of the reason for requesting the property. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false.
     */
    private AccessReason accessReason;

    /**
     * reloadCache Reloads the cache before the action.
     */
    private boolean reloadCache;

    /**
     * customAudit
     */
    private String customAudit;

    /**
     * xTenantId List of tenant IDs to enforce on the request.
     */
    private List<String> xTenantId;

    /**
     * transactionId The transaction ID to attach to the token.
     */
    private String transactionId;
}
