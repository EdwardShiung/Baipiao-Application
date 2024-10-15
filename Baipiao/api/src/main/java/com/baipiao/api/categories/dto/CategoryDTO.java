package com.baipiao.api.categories.dto;

import java.io.Serializable;

import com.baipiao.api.categories.Category;
import lombok.Data;
@Data
public class CategoryDTO  implements Serializable {
    private Long id;

    private String name;

    private String description;

    public CategoryDTO(Category category) {
        this.name = category.getName();
        this.description = category.getDescription();
        this.id = category.getId();
    }

}
