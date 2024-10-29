package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.HashObjectRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Parameters for 'hashObjects' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class HashParams extends CommonParams {

    private List<HashObjectRequest> hashObjectRequests;
}
