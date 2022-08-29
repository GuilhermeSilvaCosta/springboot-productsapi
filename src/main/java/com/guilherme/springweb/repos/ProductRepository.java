package com.guilherme.springweb.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.springweb.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
