package com.baipiao.api.categories;

import java.util.List;
import java.util.stream.Collectors;

import com.baipiao.api.categories.dto.CategoryCreateDTO;
import com.baipiao.api.categories.dto.CategoryDTO;
import com.baipiao.api.events.EventRepository;
import com.baipiao.api.events.dto.EventDTO;
import com.baipiao.api.events.dto.EventCreateDTO;
import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.venues.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<CategoryDTO> getAll() {
            return categoryRepository.findAll().stream().map(category -> {
                return new CategoryDTO(category);
            }).collect(Collectors.toList());
    }

    public CategoryDTO find(Long id) {
        return categoryRepository.findById(id).map(category -> {
            return new CategoryDTO(category);
        }).orElse(null);
    }

    public void deleteById(Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteCategoryById(id);
    }

    public CategoryDTO save(CategoryCreateDTO category) {  
        return new CategoryDTO(categoryRepository.save(category.toCategory()));
    }

    public void update(CategoryCreateDTO category, Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription()); 
        categoryRepository.updateCategory(existingCategory.getId(), existingCategory.getName(), existingCategory.getDescription());
    }
}
