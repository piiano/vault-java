package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.TokenizeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Parameters for 'tokenize' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TokenizeParams extends CommonParams {

    private List<TokenizeRequest> tokenizeRequest;

    private String expirationSecs;
}
