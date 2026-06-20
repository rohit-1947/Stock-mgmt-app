package com.rks.saas_multi_tenant_app.services.impl;

import com.rks.saas_multi_tenant_app.common.PageResponse;
import com.rks.saas_multi_tenant_app.entities.Category;
import com.rks.saas_multi_tenant_app.exceptions.DuplicateResourceException;
import com.rks.saas_multi_tenant_app.mappers.CategoryMapper;
import com.rks.saas_multi_tenant_app.repositories.CategoryRepository;
import com.rks.saas_multi_tenant_app.requests.CategoryRequest;
import com.rks.saas_multi_tenant_app.responses.CategoryResponse;
import com.rks.saas_multi_tenant_app.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void create(CategoryRequest request) {
        // check if category already exists
        checkIfCategoryExistsByName(request.getName());

        final Category category = categoryMapper.toEntity(request);
        this.categoryRepository.save(category);
    }

    @Override
    public void update(String id, CategoryRequest request) {

        final Optional<Category> existedCategory = this.categoryRepository.findById(id);
        if(existedCategory.isEmpty()){
            log.debug("Category with id {} does not exist", id);
            throw new EntityNotFoundException("Category with id "+ id + " does not exist ");
        }

        // check if category already exists
        final Category category = existedCategory.get();
        if(!category.getName().equals(request.getName())){
            checkIfCategoryExistsByName(request.getName());
        }

        final Category updatedCategory = categoryMapper.toEntity(request);
        updatedCategory.setId(id);
        this.categoryRepository.save(updatedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> findAll(final int page,final int size) {
        /*return this.categoryRepository.findAll()
                .stream()
                .map(this.categoryMapper::toResponse)
                .toList();*/
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<Category> categories = this.categoryRepository.findAll(pageRequest);
        final Page<CategoryResponse> categoryResponses = categories.map(this.categoryMapper::toResponse);
        return PageResponse.of(categoryResponses);
    }

    @Override
    public CategoryResponse findById(String id) {
        return this.categoryRepository.findById(id)
                .map(this.categoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "+ id + " does not exist"));
    }

    @Override
    public void delete(String id) {
        final Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "+ id + " does not exist"));

        this.categoryRepository.delete(category);
    }

    private void checkIfCategoryExistsByName(String name) {

        final Optional<Category> category = this.categoryRepository.findByNameIgnoreCase(name);

        if(category.isPresent()) {
            log.debug("Category with name {} already exists", name);
            throw new DuplicateResourceException("Category with name " + name + " already exists");
        }
    }
}
