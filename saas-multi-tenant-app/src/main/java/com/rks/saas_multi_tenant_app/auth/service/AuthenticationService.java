package com.rks.saas_multi_tenant_app.auth.service;

import com.rks.saas_multi_tenant_app.auth.requests.LoginRequest;
import com.rks.saas_multi_tenant_app.auth.responses.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(final LoginRequest request);
}
