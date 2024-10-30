package com.piiano.vault.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AddObjectsParams extends CommonParams {

    private List<Map<String, Object>> fields;

    private String expirationSecs;

    private Boolean isImport;

    private String exportKey;
}
