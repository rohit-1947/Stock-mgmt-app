package com.rks.saas_multi_tenant_app.services;

import com.rks.saas_multi_tenant_app.common.PageResponse;
import com.rks.saas_multi_tenant_app.requests.StockMvtRequest;
import com.rks.saas_multi_tenant_app.responses.StockMvtResponse;

public interface StockMvtService extends BasicService<StockMvtRequest, StockMvtResponse> {
    PageResponse<StockMvtResponse> findAllByProductId(String productId, int page, int size);
}
