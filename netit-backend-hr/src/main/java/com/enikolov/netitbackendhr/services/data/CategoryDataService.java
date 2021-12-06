package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.extra.Category;
import com.enikolov.netitbackendhr.repositories.extra.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDataService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }
}
