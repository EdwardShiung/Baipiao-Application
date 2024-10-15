package com.baipiao.api.organizations;

import java.util.List;
import java.util.stream.Collectors;

import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.organizations.dto.OrganizationCreateDTO;
import com.baipiao.api.organizations.dto.OrganizationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    
    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
    public List<OrganizationDTO> getAll() {
            return organizationRepository.findAllOrganizations().stream().map(organization -> {
                System.out.println(organization);
                return new OrganizationDTO(organization);
            }).collect(Collectors.toList());
    }

    public OrganizationDTO find(Long id) {
        return organizationRepository.findById(id).map(organization -> {
            return new OrganizationDTO(organization);
        }).orElse(null);
    }

    public void deleteById(Long id) {
        Organization existingOrganization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException(id));
        organizationRepository.deleteOrganizationById(id);
    }

    public void save(OrganizationCreateDTO newOrganization) {  
        Organization organization = new Organization();
        organization.setName(newOrganization.getName());
        organization.setDescription(newOrganization.getDescription());
        organization.setEmail(newOrganization.getEmail());
        organization.setPhoneno(newOrganization.getPhoneno());
        organizationRepository.insertOrganization( organization.getName(), organization.getDescription(), organization.getEmail(),organization.getPhoneno());
        
    }

    public void update(OrganizationCreateDTO newOrganization, Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException(id));
        organization.setName(newOrganization.getName());
        organization.setDescription(newOrganization.getDescription());
        organization.setEmail(newOrganization.getEmail());
        organization.setPhoneno(newOrganization.getPhoneno());

        organizationRepository.updateOrganization(organization.getId(), organization.getName(), organization.getDescription(), organization.getEmail(),organization.getPhoneno());
    }
}
