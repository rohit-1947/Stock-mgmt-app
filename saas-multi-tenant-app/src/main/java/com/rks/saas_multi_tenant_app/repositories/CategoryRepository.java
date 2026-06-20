package com.rks.saas_multi_tenant_app.repositories;


import com.rks.saas_multi_tenant_app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
//    void save(Category category);

    Optional<Category> findByNameIgnoreCase(String name);

//    Optional<Category> findById(String id);

//    void delete(Category category);
}
