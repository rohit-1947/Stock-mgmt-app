//package com.rks.saas_multi_tenant_app.config;
//
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.hibernate.Session;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
////@Aspect
////@Component
//public class TenantHibernateFilter {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Before("execution(* com.rks.saas_multi_tenant_app.repositories.*.*(..))")
//    public void activateFilter() {
//        final String tenantId = TenantContext.getCurrentTenant();
//        if(tenantId != null) {
//            final Session session = entityManager.unwrap(Session.class);
//
//            //Activate the filter to inject the TenantId
//            session.enableFilter("tenantFilter")
//                    .setParameter("tenantId", tenantId);
//        }
//    }
//}
