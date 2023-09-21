package io.github.darllinsonazvd.sales.domain.repository;

import io.github.darllinsonazvd.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o left join fetch o.items where o.id = :id")
    Optional<Order> findByIdFetchItems(@Param("id") Integer id);
}
