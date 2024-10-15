package com.baipiao.api.organizations.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.organizations.Organization;
import lombok.Data;

@Data
public class OrganizationCreateDTO implements Serializable {
    private Long id;

    private String name;

    private String description;

    private String email;

    private String phoneno;

    
    public OrganizationCreateDTO() {
    }

    public OrganizationCreateDTO(Organization organization) {
      this.id = organization.getId();
      this.name = organization.getName();
      this.description = organization.getDescription();
      this.email = organization.getEmail();
      this.phoneno = organization.getPhoneno();

    }
}