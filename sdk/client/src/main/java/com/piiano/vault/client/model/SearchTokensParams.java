package com.piiano.vault.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * Parameters for 'searchTokens' API.
 */
@Data
@Builder
public class SearchTokensParams {

    /**
     * collection The name of the collection containing the objects. (required)
     */
    private String collection;

    /**
     * accessReason Details of the reason for requesting the property. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false. (required)
     */
     private AccessReason accessReason;

    /**
     * reloadCache Reloads the cache before the action. (optional)
     */
    private Boolean reloadCache;
}
