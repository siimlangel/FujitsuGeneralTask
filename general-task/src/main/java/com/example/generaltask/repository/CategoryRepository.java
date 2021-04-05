package com.example.generaltask.repository;

import com.example.generaltask.model.Category;
import com.example.generaltask.model.Feedback;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Long> {
    /**
     *  Find category by type
     * @param type Unique type of category to find
     * @return Category defined by an unique type
     */
    Category findByType(String type);
}
