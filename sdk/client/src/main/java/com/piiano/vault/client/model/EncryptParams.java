package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.EncryptionRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Parameters for 'encrypt' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EncryptParams extends CommonParams {

    private List<EncryptionRequest> encryptionRequest;

    private String expirationSecs;
}
