package com.rks.saas_multi_tenant_app.mappers;


import com.rks.saas_multi_tenant_app.entities.Category;
import com.rks.saas_multi_tenant_app.requests.CategoryRequest;
import com.rks.saas_multi_tenant_app.responses.CategoryResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CategoryMapper {

    public Category toEntity(final CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .deleted(false)
                .build();
    }

    public CategoryResponse toResponse(final Category entity){
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
