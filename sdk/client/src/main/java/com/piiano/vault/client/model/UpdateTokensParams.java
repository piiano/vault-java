package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.UpdateTokenRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Parameters for 'updateTokens' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UpdateTokensParams extends CommonParams {

    private UpdateTokenRequest updateTokenRequest;

    private TokenQuery tokenQuery;

    private Set<String> options;

    private String expirationSecs;
}
