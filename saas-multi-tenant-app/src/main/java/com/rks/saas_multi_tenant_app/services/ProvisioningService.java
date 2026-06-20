package com.rks.saas_multi_tenant_app.services;

import com.rks.saas_multi_tenant_app.entities.Tenant;

public interface ProvisioningService {

    void provisionTenant(final Tenant tenant);

}
