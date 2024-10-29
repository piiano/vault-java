package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ListObjectsParams extends CommonParams {

    private List<UUID> objectIds;

    private List<String> props;

    private Integer pageSize;

    private Set<String> options;
}
