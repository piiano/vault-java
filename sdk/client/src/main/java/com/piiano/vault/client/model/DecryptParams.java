package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.DecryptionRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

/**
 * Parameters for 'decrypt' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DecryptParams extends CommonParams {

    private List<DecryptionRequest> decryptionRequests;

    private Set<String> options;
}
