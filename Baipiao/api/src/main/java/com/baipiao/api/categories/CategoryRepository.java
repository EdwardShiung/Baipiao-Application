package com.baipiao.api.categories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Read: Retrieve all categories
    @Query(value = "SELECT * FROM categories", nativeQuery = true)
    List<Category> findAllCategories();

    // Read: Retrieve a category by its ID
    @Query(value = "SELECT * FROM categories WHERE id = :id", nativeQuery = true)
    Category findCategoryById(@Param("id") Long id);

    // Read: Retrieve a category by its name
    @Query(value = "SELECT * FROM categories WHERE name = :name", nativeQuery = true)
    Category findCategoryByName(@Param("name") String name);

    // Create: Insert a new category (not typically done in a repository, but here's a native query version)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categories (name, description) VALUES (:name, :description)", nativeQuery = true)
    void insertCategory(@Param("name") String name, @Param("description") String description);

    // Update: Update a category by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE categories SET name = :name, description = :description WHERE id = :id", nativeQuery = true)
    void updateCategory(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

    // Delete: Delete a category by its ID
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categories WHERE id = :id", nativeQuery = true)
    void deleteCategoryById(@Param("id") Long id);
}