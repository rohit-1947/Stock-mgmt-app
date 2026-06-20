package com.rks.saas_multi_tenant_app.repositories;

import com.rks.saas_multi_tenant_app.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

    boolean existsByCompanyCode(String companyCode);

    boolean existsByEmail(String email);
}
