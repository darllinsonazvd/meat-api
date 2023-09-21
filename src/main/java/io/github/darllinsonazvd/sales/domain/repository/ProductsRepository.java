package io.github.darllinsonazvd.sales.domain.repository;

import io.github.darllinsonazvd.sales.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
