package com.rks.saas_multi_tenant_app.exceptions;

public class TenantProvisioningException extends BusinessException {
    public TenantProvisioningException(final String message) {
        super(message);
    }
}
