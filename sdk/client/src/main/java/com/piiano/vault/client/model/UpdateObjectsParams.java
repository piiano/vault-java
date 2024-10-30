package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UpdateObjectsParams extends CommonParams {

    private List<Map<String, Object>> objects;

    private Set<String> options;

    private String expirationSecs;

    private Boolean isImport;

    private String exportKey;
}
