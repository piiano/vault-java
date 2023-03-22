package com.piiano.vault.client.model;

import lombok.Getter;

/**
 * Available values for the access reason, for "Other" an ad-hoc reason can be provided.
 */
@Getter
public enum AccessReason {

    AppFunctionality("AppFunctionality"),
    Analytics("Analytics"),
    Notifications("Notifications"),
    ThirdPartyMarketing("ThirdPartyMarketing"),
    Marketing("Marketing"),
    FraudPreventionSecurityAndCompliance("FraudPreventionSecurityAndCompliance"),
    AccountManagement("AccountManagement"),
    Maintenance("Maintenance"),
    DataSubjectRequest("DataSubjectRequest"),
    Other("Other")
            {
                public AccessReason adhocReason(String adhocReason) {
                    this.adhocReason = adhocReason;
                    return this;
                }

                @Override
                public String toString() {
                    return "Other:" + this.adhocReason;
                }
            };

    // Details of the access reason. The default is set when no access reason is provided and PVAULT_SERVICE_FORCE_ACCESS_REASON is false. (required)
    private final String reason;

    // An ad-hoc reason for accessing the Vault data. (optional)
    protected String adhocReason;


    AccessReason(String reason) {
        this.reason = reason;
        this.adhocReason = "";
    }

    public AccessReason adhocReason(String adhocReason) {
        if (Other == this) {
            return Other.adhocReason(adhocReason);
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(reason);
    }
}
