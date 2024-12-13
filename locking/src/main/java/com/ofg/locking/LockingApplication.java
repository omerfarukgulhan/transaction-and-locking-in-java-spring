package com.ofg.locking;

import com.ofg.locking.model.Product;
import com.ofg.locking.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LockingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LockingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product(null, "Product 1", 100L, null);
            Product product2 = new Product(null, "Product 2", 200L, null);

            productRepository.save(product1);
            productRepository.save(product2);
        };
    }
}
