package com.baipiao.api.categories;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id){
        super("Category with id " + id + " not found");
    }
}
