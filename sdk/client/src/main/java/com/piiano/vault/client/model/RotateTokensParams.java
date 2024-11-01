package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Parameters for 'rotateTokens' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RotateTokensParams extends CommonParams {

    List<String> tokenIds;
}
