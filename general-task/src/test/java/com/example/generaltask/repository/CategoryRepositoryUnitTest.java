package com.example.generaltask.repository;

import com.example.generaltask.Application;
import com.example.generaltask.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class CategoryRepositoryUnitTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findById_OneCategorySaved_CategoryIdEqual() {
        Category cat = new Category(null, "Type");

        Category saved = categoryRepository.save(cat);

        Optional<Category> byId = categoryRepository.findById(saved.getId());

        assertTrue(byId.isPresent());

        assertEquals(saved.getId(), byId.get().getId());
    }


    @Test
    public void findById_ManyCategoriesSaved_CategoryIdEqual() {
        Category cat1 = new Category(null, "Type1");
        Category cat2 = new Category(null, "Type2");
        Category cat3 = new Category(null, "Type3");

        categoryRepository.save(cat1);
        Category saved2 = categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        Optional<Category> byId = categoryRepository.findById(saved2.getId());

        assertTrue(byId.isPresent());

        assertEquals(saved2.getId(), byId.get().getId());
    }

    @Test
    public void findById_NonExistingCategory_CategoryFetchedNotPresent() {
        Category type1 = new Category(null, "Type1");
        categoryRepository.save(type1);

        Optional<Category> byId = categoryRepository.findById(-1L);

        assertTrue(byId.isEmpty());

    }

    @Test
    public void findByType_OneCategorySaved_FetchedNotNullTypeEqualToSaved() {
        Category cat = new Category(null, "Type1");

        Category saved = categoryRepository.save(cat);

        Category byType = categoryRepository.findByType(saved.getType());

        assertNotNull(byType);
        assertEquals(saved.getType(), byType.getType());
    }

    @Test
    public void findByType_ManyCategoriesSaved_FetchedNotNullTypeEqualToSaved() {
        Category cat1 = new Category(null, "Type1");
        Category cat2 = new Category(null, "Type2");
        Category cat3 = new Category(null, "Type3");

        categoryRepository.save(cat1);
        Category saved = categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        Category byType = categoryRepository.findByType(saved.getType());

        assertNotNull(byType);
        assertEquals(saved.getType(), byType.getType());
    }

    @Test
    public void findByType_NonExistingCategory_CategoryFetchedNull() {
        Category cat1 = new Category(null, "Type1");
        Category cat2 = new Category(null, "Type2");
        Category cat3 = new Category(null, "Type3");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        Category byType = categoryRepository.findByType("NotRight");

        assertNull(byType);

    }

    @Test
    public void count_GetCountOfSavedCategories_CountEqualToActualAmountSaved() {
        Category cat1 = new Category(null, "Type1");
        Category cat2 = new Category(null, "Type2");
        Category cat3 = new Category(null, "Type3");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        long count = categoryRepository.count();

        assertEquals(3L, count);
    }

    @Test
    public void findAll_FindAllCategories_FoundAmountEqualToActualAmountSaved() {
        Category cat1 = new Category(null, "Type1");
        Category cat2 = new Category(null, "Type2");
        Category cat3 = new Category(null, "Type3");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        List<Category> found = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(found::add);

        assertEquals(3, found.size());
    }

}
