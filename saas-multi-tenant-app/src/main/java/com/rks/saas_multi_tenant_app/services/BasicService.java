package com.rks.saas_multi_tenant_app.services;


import com.rks.saas_multi_tenant_app.common.PageResponse;

import java.util.List;

public interface BasicService<I, O> {

    void create(final I request);

    void update(final String id, final I request);

    PageResponse<O> findAll(final int page, final int size);

    O findById(final String id);

    void delete(final String id);
}
