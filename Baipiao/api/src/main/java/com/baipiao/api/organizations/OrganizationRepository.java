package com.baipiao.api.organizations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Override
    @Query(value = "SELECT * FROM organization", nativeQuery = true)
    List<Organization> findAll();

    // Example for filtering by name
    @Query(value = "SELECT * FROM organization WHERE name LIKE %:name%", nativeQuery = true)
    List<Organization> findByNameContaining(@Param("name") String name);

    // Example for filtering by email
    @Query(value = "SELECT * FROM organization WHERE email = :email", nativeQuery = true)
    Organization findByEmail(@Param("email") String email);

    // Example for filtering by address
    @Query(value = "SELECT * FROM organization WHERE address LIKE %:address%", nativeQuery = true)
    List<Organization> findByAddressContaining(@Param("address") String address);

    // Example for filtering by phone number
    @Query(value = "SELECT * FROM organization WHERE phoneno = :phoneno", nativeQuery = true)
    Organization findByPhoneno(@Param("phoneno") String phoneno);
}