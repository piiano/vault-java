package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AddObjectParams extends CommonParams {

    private final Map<String, Object> fields;
    private String expirationSecs;
    private Boolean isImport;
    private String exportKey;
}
