package jp.projects.ifood_challenge.controllers;

import jp.projects.ifood_challenge.domain.category.Category;
import jp.projects.ifood_challenge.domain.category.CategoryDTO;
import jp.projects.ifood_challenge.domain.product.Product;
import jp.projects.ifood_challenge.domain.product.ProductDTO;
import jp.projects.ifood_challenge.services.CategoryService;
import jp.projects.ifood_challenge.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.created(null).body(this.productService.insert(productDTO));
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(this.productService.update(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteCategory(@PathVariable("id") String id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
