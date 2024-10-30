package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.ObjectID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

/**
 * Parameters for 'deleteObjects' API.
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DeleteObjectsParams extends CommonParams {

    private List<ObjectID> objectIds;

    private Set<String> options;
}
