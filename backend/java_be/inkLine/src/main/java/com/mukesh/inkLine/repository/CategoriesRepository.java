package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, UUID> {
    Optional<Categories> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
}
