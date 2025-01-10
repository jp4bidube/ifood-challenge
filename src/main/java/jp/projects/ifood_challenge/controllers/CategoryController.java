package jp.projects.ifood_challenge.controllers;

import jp.projects.ifood_challenge.domain.category.Category;
import jp.projects.ifood_challenge.domain.category.CategoryDTO;
import jp.projects.ifood_challenge.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryPayload) {
        return ResponseEntity.ok().body(this.categoryService.insert(categoryPayload));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(this.categoryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO categoryPayload) {
        return ResponseEntity.ok().body(this.categoryService.update(id, categoryPayload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") String id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
