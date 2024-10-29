package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

/**
 * Parameters for 'deleteTokens' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DeleteTokensParams extends CommonParams {

    private TokenQuery tokenQuery;

    private List<String> props;

    private Set<String> options;

    private String tenantId;
}
