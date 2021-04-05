package com.example.generaltask.service;

import com.example.generaltask.model.Category;
import com.example.generaltask.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Find all categories
     * @return List of all categories
     */
    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
