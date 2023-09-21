package io.github.darllinsonazvd.sales.domain.repository;

import io.github.darllinsonazvd.sales.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customer, Integer> {
}
