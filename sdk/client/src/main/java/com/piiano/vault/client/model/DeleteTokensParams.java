package com.piiano.vault.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * Parameters for 'deleteTokens' API.
 */
@Data
@Builder
public class DeleteTokensParams {

    /**
     * collection The name of the collection containing the objects. (required)
     */
    private String collection;

    /**
     * tenantId The tenant ID of the owning object. (optional)
     */
    private String tenantId;

    /**
     * accessReason Details of the reason for requesting the property. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false. (required)
     */
     private AccessReason accessReason;

    /**
     * reloadCache Reloads the cache before the action. (optional)
     */
    private Boolean reloadCache;
}
