package com.rks.saas_multi_tenant_app.mappers;

import com.rks.saas_multi_tenant_app.entities.Product;
import com.rks.saas_multi_tenant_app.entities.StockMvt;
import com.rks.saas_multi_tenant_app.requests.StockMvtRequest;
import com.rks.saas_multi_tenant_app.responses.StockMvtResponse;
import org.springframework.stereotype.Component;


@Component
public class StockMvtMapper {

    public StockMvt toEntity(final StockMvtRequest request) {
        return StockMvt.builder()
                .dateMvt(request.getDateMvt())
                .comment(request.getComment())
                .typeMvt(request.getTypeMvt())
                .quantity(request.getQuantity())
                .product(Product.builder()
                        .id(request.getProductId())
                        .build())
                .deleted(false)
                .build();
    }

    public StockMvtResponse toResponse(final StockMvt entity) {
        return StockMvtResponse.builder()
                .id(entity.getId())
                .dateMvt(entity.getDateMvt())
                .comment(entity.getComment())
                .typeMvt(entity.getTypeMvt())
                .quantity(entity.getQuantity())
                .build();
    }
}
