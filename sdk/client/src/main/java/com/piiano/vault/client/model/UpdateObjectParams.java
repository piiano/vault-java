package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UpdateObjectParams extends CommonParams {

    private UUID objectId;

    private Map<String, Object> fields;

    private Set<String> options;

    private String expirationSecs;

    private Boolean isImport;

    private String exportKey;
}
