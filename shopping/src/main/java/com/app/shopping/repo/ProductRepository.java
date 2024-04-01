package com.app.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.shopping.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
