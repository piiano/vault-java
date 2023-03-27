package com.piiano.vault.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * Default parameters for the Vault Clients. These parameters are used by the Vault Client for
 * all requests unless set by the request parameters.
 */
@Data
@Builder
public class DefaultParams {

    private String collection;

    private AccessReason accessReason;

    private String expirationSecs;

    private boolean reloadCache;
}
