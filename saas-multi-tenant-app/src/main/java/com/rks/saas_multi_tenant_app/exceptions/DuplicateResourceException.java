package com.rks.saas_multi_tenant_app.exceptions;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(final String message) {
        super(message);
    }
}
