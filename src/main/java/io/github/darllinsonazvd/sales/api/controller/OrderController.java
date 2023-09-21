package io.github.darllinsonazvd.sales.api.controller;

import io.github.darllinsonazvd.sales.api.dto.ItemsOrderDetailsDTO;
import io.github.darllinsonazvd.sales.api.dto.OrderDTO;
import io.github.darllinsonazvd.sales.api.dto.OrderDetailsDTO;
import io.github.darllinsonazvd.sales.api.dto.UpdateOrderStatusDTO;
import io.github.darllinsonazvd.sales.domain.entity.ItemOrder;
import io.github.darllinsonazvd.sales.domain.entity.Order;
import io.github.darllinsonazvd.sales.domain.enums.OrderStatus;
import io.github.darllinsonazvd.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.save(orderDTO);
        return order.getId();
    }

    @GetMapping("/{id}")
    public OrderDetailsDTO getOrderDetails(@PathVariable Integer id) {
        return orderService
                .getOrderDetails(id)
                .map(order -> convert(order))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for id: " + id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody UpdateOrderStatusDTO dto) {
        String status = dto.getStatus();
        orderService.updateStatus(id, OrderStatus.valueOf(status));
    }

    private OrderDetailsDTO convert(Order order) {
        return OrderDetailsDTO
                .builder()
                .id(order.getId())
                .dateTime(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(order.getCustomer().getCpf())
                .customerName(order.getCustomer().getName())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .items(convertItemOrderToDTO(order.getItems()))
                .build();
    }

    private List<ItemsOrderDetailsDTO> convertItemOrderToDTO(List<ItemOrder> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream().map(
                item -> ItemsOrderDetailsDTO
                        .builder()
                        .description(item.getProduct().getDescription())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }
}
