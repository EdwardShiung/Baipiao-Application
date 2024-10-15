package com.baipiao.api.organizations;

import java.util.List;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    // Read: Retrieve all organizations
    @Query(value = "SELECT * FROM organizations", nativeQuery = true)
    List<Organization> findAllOrganizations();

    // Read: Retrieve a organization by its ID
    @Query(value = "SELECT * FROM organizations WHERE id = :id", nativeQuery = true)
    Organization findOrganizationById(@Param("id") Long id);

    // Read: Retrieve a organization by its name
    @Query(value = "SELECT * FROM organizations WHERE name = :name", nativeQuery = true)
    Organization findOrganizationByName(@Param("name") String name);

    // Create: Insert a new organization (not typically done in a repository, but here's a
    // native query version)
    // Create: Insert a new organization
    @Modifying
    @Transactional

    @Query(value = "INSERT INTO organizations (name, description, email, phoneno) VALUES (:name, :description, :email, :phoneno )", nativeQuery = true)
    void insertOrganization(@Param("name") String name, @Param("description") String description, @Param("email") String email, @Param("phoneno") String phoneno);

    // Update: Update a organization by its ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE organizations SET name = :name, description = :description , email = :email, phoneno = :phoneno WHERE id = :id", nativeQuery = true)
    void updateOrganization(@Param("id") Long id, @Param("name") String name, @Param("description") String description, @Param("email") String email, @Param("phoneno") String phoneno);

    // Delete: Delete a organization by its ID
    @Modifying
    @Transactional
    // @Query(value = "DELETE FROM organizations WHERE id = :id", nativeQuery = true)
    void deleteOrganizationById(@Param("id") Long id);

}