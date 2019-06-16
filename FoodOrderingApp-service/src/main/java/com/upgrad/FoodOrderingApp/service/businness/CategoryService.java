package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestaurantDao restaurantDao;

    /* Method to get the list of al Categories */
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return categoryDao.getAllCategories().stream()
                .sorted(Comparator.comparing(CategoryEntity::getCategoryName))
                .collect(Collectors.toList());
    }

    /* Method to fetch category based on UUID else will throw Category not found exception */
    public CategoryEntity getCategoryById(String categoryUuid) throws CategoryNotFoundException {
        if (categoryUuid.equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryByUuid(categoryUuid);

        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        return categoryEntity;
    }

    /* Method to get the list of categories of restaurant based on restaurants UUID */
    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantUUID) {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(restaurantUUID);
        return restaurantEntity.getCategories().stream()
                .sorted(Comparator.comparing(CategoryEntity::getCategoryName))
                .collect(Collectors.toList());
    }
}
