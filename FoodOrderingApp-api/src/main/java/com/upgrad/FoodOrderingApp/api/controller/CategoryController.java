package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*To retrieve all the categories ordered by the name*/

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() {

        // get all categories ordered by name
        List<CategoryEntity> categoryEntityList = categoryService.getAllCategoriesOrderedByName();

        // create response
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            CategoryListResponse categoryListResponse = new CategoryListResponse()
                    .id(UUID.fromString(categoryEntity.getUuid()))
                    .categoryName(categoryEntity.getCategoryName());
            categoriesListResponse.addCategoriesItem(categoryListResponse);
        }

        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse, HttpStatus.OK);
    }

    /* To fetch category based on category uuid*/
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryById(
            @PathVariable("category_id") final String categoryId)
            throws CategoryNotFoundException
    {
        // Get all items for category
        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

        // Create response
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse().id(UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName());

        for (ItemEntity itemEntity : categoryEntity.getItems()) {
            ItemList itemList = new ItemList()
                    .id(UUID.fromString(itemEntity.getUuid()))
                    .itemName(itemEntity.getItemName())
                    .price(itemEntity.getPrice())
                    .itemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType().getValue()));
            categoryDetailsResponse.addItemListItem(itemList);
        }

        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse, HttpStatus.OK);
    }

}
