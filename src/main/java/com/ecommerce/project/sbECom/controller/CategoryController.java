package com.ecommerce.project.sbECom.controller;


import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") //same word like api here can be defined here
public class CategoryController {
    @Autowired //field injection
    private CategoryService service;

//    public CategoryController(CategoryService service) { // constructor injection
//        this.service = service;
//    }

    @GetMapping("public/categories")
    //@RequestMapping(value = "api/public/categories" , method = RequestMethod.GET)
    //it can be used universally
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(service.getAllCategories() , HttpStatus.OK);
    }

    @PostMapping("public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        service.createCategory(category);
        return ResponseEntity.ok("Category created successfully");
    }

    @DeleteMapping("admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String status = service.deleteCategory(categoryId);

            return new ResponseEntity<>(status , HttpStatus.OK);
            //return ResponseEntity.status(HttpStatus.OK).body(status);
            //return ResponseEntity.ok(status);
        }
        catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

    @PutMapping("public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category ,@PathVariable Long categoryId){
        try{
            String status = service.updateCategory(category , categoryId);
            return ResponseEntity.ok(status);
        }
        catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }


}
