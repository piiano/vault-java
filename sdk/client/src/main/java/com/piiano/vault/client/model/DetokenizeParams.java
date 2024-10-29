package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

/**
 * Parameters for 'detokenize' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DetokenizeParams extends CommonParams {

    private TokenQuery tokenQuery;

    private List<String> props;

    private Set<String> options;
}
