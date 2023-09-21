package io.github.darllinsonazvd.sales.service;

import io.github.darllinsonazvd.sales.api.dto.OrderDTO;
import io.github.darllinsonazvd.sales.domain.entity.Order;
import io.github.darllinsonazvd.sales.domain.enums.OrderStatus;

import java.util.Optional;

public interface OrderService {
    Order save(OrderDTO orderDTO);
    Optional<Order> getOrderDetails(Integer id);
    void updateStatus(Integer id, OrderStatus status);
}
