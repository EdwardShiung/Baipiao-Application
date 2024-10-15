package com.baipiao.api.categories.dto;

import java.io.Serializable;

import com.baipiao.api.categories.Category;
import lombok.Data;
@Data
public class CategoryCreateDTO  implements Serializable {
    private Long id;

    private String name;

    private String description;

    public CategoryCreateDTO(){}
    public CategoryCreateDTO(Category category) {
        this.name = category.getName();
        this.description = category.getDescription();
        this.id = category.getId();
    }
    public Category toCategory() {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

}
