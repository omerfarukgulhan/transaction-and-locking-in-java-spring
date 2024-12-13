package com.ofg.locking.service;

import com.ofg.locking.model.Product;
import com.ofg.locking.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product getProductWithPessimisticLock(Long id) {
        return productRepository.findWithPessimisticLock(id);
    }

    @Transactional
    public Product updateProductWithPessimisticLock(Long id, Long price) {
        productRepository.updateProductPrice(id, price);
        return productRepository.findWithPessimisticLock(id);
    }

    @Transactional
    public boolean makeDiscount(Long productId, int discountPercentage, String type) {
        Product product;
        if ("pessimistic".equals(type)) {
            product = productRepository.findWithPessimisticLock(productId);
        } else if ("optimistic".equals(type)) {
            product = productRepository.findWithOptimisticLock(productId);
        } else {
            product = productRepository.findById(productId).orElse(null);
        }

        if (product == null || product.getPrice() <= 0) {
            return false;
        }

        product.setPrice(product.getPrice() - product.getPrice() / 100 * discountPercentage);
        productRepository.save(product);
        return true;
    }

    @Transactional
    public Product updateProduct(Long id, Long price) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setPrice(price);
        return productRepository.save(product);
    }
}
