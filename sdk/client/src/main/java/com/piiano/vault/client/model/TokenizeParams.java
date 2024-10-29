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

    /**
     * expirationSecs Token expiration time in seconds. If not set, the default expiration time is used. See the &#x60;PVAULT_EXPIRATION_TOKENS&#x60; variable. (optional)
     */
    private String expirationSecs;
}
