package com.piiano.vault.client.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
public class TokenQuery {

    private List<String> tokenIds;

    private List<UUID> objectIds;

    private List<String> tags;
}
