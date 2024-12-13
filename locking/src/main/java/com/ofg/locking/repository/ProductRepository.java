package com.ofg.locking.repository;

import com.ofg.locking.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findWithPessimisticLock(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findWithOptimisticLock(Long id);

    @Modifying
    @Query("UPDATE Product p SET p.price = :stock WHERE p.id = :id")
    void updateProductPrice(@Param("id") Long id, @Param("stock") Long stock);
}
