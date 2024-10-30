package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * Parameters for 'searchTokens' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SearchTokensParams extends CommonParams {

    private TokenQuery tokenQuery;

    private Set<String> options;
}
