package com.rks.saas_multi_tenant_app.controllers;


import com.rks.saas_multi_tenant_app.common.PageResponse;
import com.rks.saas_multi_tenant_app.requests.CategoryRequest;
import com.rks.saas_multi_tenant_app.responses.CategoryResponse;
import com.rks.saas_multi_tenant_app.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @Valid
            @RequestBody
            final CategoryRequest categoryRequest
    ) {
        this.categoryService.create(categoryRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Void> updateCategory(
            @Valid
            @RequestBody
            final CategoryRequest categoryRequest,
            @PathVariable("category-id")
            final String id
    ) {
        this.categoryService.update(id, categoryRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "10")
            final int size
    ) {
        return ResponseEntity.ok(this.categoryService.findAll(page,size));
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable(value = "category-id")
            final String id
    ) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategoryById(
            @PathVariable("category-id")
            final String id
    ){
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
