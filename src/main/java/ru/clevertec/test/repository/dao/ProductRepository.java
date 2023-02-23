package ru.clevertec.test.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.test.repository.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
