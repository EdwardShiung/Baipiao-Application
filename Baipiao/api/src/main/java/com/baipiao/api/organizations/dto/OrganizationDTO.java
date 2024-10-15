package com.baipiao.api.organizations.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baipiao.api.organizations.Organization;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class OrganizationDTO implements Serializable {
    private Long id;
    private String name;

    private String description;

    private String email;

    private String phoneno;

    public OrganizationDTO() {
    }

    public OrganizationDTO(Organization organization) {
        this.id = organization.getId(); 
        this.name = organization.getName();
        this.description = organization.getDescription();
        this.email = organization.getEmail();
        this.phoneno = organization.getPhoneno();
    }
}