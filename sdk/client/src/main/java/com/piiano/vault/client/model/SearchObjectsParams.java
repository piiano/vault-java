package com.piiano.vault.client.model;

import com.piiano.vault.client.openapi.model.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SearchObjectsParams extends CommonParams {

    private Query query;

    private List<String> props;

    private Set<String> options;

    private String cursor;

    private Integer pageSize;

    private String xTransParam;
}
