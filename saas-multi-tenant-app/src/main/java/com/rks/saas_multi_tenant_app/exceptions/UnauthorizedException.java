package com.rks.saas_multi_tenant_app.exceptions;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(final String message) {
        super(message);
    }
}
