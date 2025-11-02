package com.hackathon.petstore_cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.hackathon.petstore_cms.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByType(String type);
    
    // This is for Module 6: Smart Inventory Alert
    @Query("SELECT p FROM Product p WHERE p.stockQuantity < 10")
    List<Product> findLowStockProducts();

    // This is for Module 6: Inventory Value Report
    @Query("SELECT SUM(p.price * p.stockQuantity) FROM Product p")
    Double getTotalInventoryValue();

    // This is for Module 6: Inventory Count Report
    @Query("SELECT SUM(p.stockQuantity) FROM Product p")
    Integer getTotalStockCount();
    
    // This is for the search bar
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1% OR p.category LIKE %?1% OR p.type LIKE %?1%")
    List<Product> search(String keyword);
}