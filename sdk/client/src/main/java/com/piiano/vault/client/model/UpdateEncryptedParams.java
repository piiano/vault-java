package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.UpdateEncryptionRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

/**
 * Parameters for 'updateEncrypted' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UpdateEncryptedParams extends CommonParams {

    private List<UpdateEncryptionRequest> updateEncryptionRequests;

    private Set<String> options;

    private String expirationSecs;
}
