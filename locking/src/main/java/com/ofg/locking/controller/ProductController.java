package com.ofg.locking.controller;

import com.ofg.locking.model.Product;
import com.ofg.locking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProductWithPessimisticLock(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/update-price")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @RequestParam Long price) {
        Product updatedProduct = productService.updateProductWithPessimisticLock(id, price);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/{id}/discount")
    public ResponseEntity<Boolean> makeDiscount(@PathVariable Long id,
                                                @RequestParam int discountPercentage,
                                                @RequestParam String type) {
        boolean success = productService.makeDiscount(id, discountPercentage, type);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam Long price) {
        Product updatedProduct = productService.updateProduct(id, price);
        return ResponseEntity.ok(updatedProduct);
    }
}

