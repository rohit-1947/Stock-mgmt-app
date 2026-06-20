package com.rks.saas_multi_tenant_app.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String message;

    public BusinessException(final String message) {
        super(message);
        this.message = message;
    }
}
