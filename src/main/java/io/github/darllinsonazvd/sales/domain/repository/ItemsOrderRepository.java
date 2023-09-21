package io.github.darllinsonazvd.sales.domain.repository;

import io.github.darllinsonazvd.sales.domain.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsOrderRepository extends JpaRepository<ItemOrder, Integer> {
}
