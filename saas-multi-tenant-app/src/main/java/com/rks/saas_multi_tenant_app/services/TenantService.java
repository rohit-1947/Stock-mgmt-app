package com.rks.saas_multi_tenant_app.services;

import com.rks.saas_multi_tenant_app.common.PageResponse;
import com.rks.saas_multi_tenant_app.requests.RegisterTenantRequest;
import com.rks.saas_multi_tenant_app.responses.TenantResponse;

public interface TenantService {

    void registerTenant(final RegisterTenantRequest request);

    void approveTenant(final String tenantId);

    void activateTenant(final String tenantId);

    void deactivateTenant(final String tenantId);

    void suspendTenant(final String tenantId);

    PageResponse<TenantResponse> findAll(final int page, final int size);

}
