package io.github.darllinsonazvd.sales.service.impl;

import io.github.darllinsonazvd.sales.api.dto.ItemOrderDTO;
import io.github.darllinsonazvd.sales.api.dto.OrderDTO;
import io.github.darllinsonazvd.sales.domain.entity.Customer;
import io.github.darllinsonazvd.sales.domain.entity.ItemOrder;
import io.github.darllinsonazvd.sales.domain.entity.Order;
import io.github.darllinsonazvd.sales.domain.entity.Product;
import io.github.darllinsonazvd.sales.domain.enums.OrderStatus;
import io.github.darllinsonazvd.sales.domain.repository.CustomersRepository;
import io.github.darllinsonazvd.sales.domain.repository.ItemsOrderRepository;
import io.github.darllinsonazvd.sales.domain.repository.OrdersRepository;
import io.github.darllinsonazvd.sales.domain.repository.ProductsRepository;
import io.github.darllinsonazvd.sales.exception.HandleException;
import io.github.darllinsonazvd.sales.exception.OrderNotFoundException;
import io.github.darllinsonazvd.sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;

    private final CustomersRepository customersRepository;

    private final ProductsRepository productsRepository;

    private final ItemsOrderRepository itemsOrderRepository;

    /**
     * Salvar pedido do cliente
     *
     * @param orderDTO Objeto recebido da requisição
     * @return Pedido criado e salvo na base de dados
     */
    @Override
    @Transactional
    public Order save(OrderDTO orderDTO) {
        // Recuperando ID do cliente e buscando na base de dados
        Integer customerId = orderDTO.getCustomerId();
        Customer customer = customersRepository
                .findById(customerId)
                .orElseThrow(() -> new HandleException("Invalid customer id: " + customerId));

        Order order = new Order();
        order.setTotal(orderDTO.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.setStatus(OrderStatus.FINISHED);

        List<ItemOrder> itemsOrder = this.parseOrderItems(order, orderDTO.getItems());

        order.setItems(itemsOrder);
        ordersRepository.save(order);
        itemsOrderRepository.saveAll(itemsOrder);

        return order;
    }

    @Override
    public Optional<Order> getOrderDetails(Integer id) {
        return ordersRepository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, OrderStatus status) {
        ordersRepository
                .findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return ordersRepository.save(order);
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Converter items do pedido de DTO para Object
     *
     * @param order Pedido do cliente
     * @param items Lista de DTOs de itens do pedido
     * @return Lista de itens do pedido no formato ItemOrder (Object)
     */
    private List<ItemOrder> parseOrderItems(Order order, List<ItemOrderDTO> items) {
        if (items.isEmpty()) {
            throw new HandleException("It is not possible to place order without items.");
        }

        return items
                .stream()
                .map(dto -> {
                    // Recuperando ID do produto e buscando na base de dados
                    Integer productId = dto.getProductId();
                    Product product = productsRepository
                            .findById(productId)
                            .orElseThrow(() -> new HandleException("Invalid product id: " + productId));


                    ItemOrder itemOrder = new ItemOrder();
                    itemOrder.setQuantity(dto.getQuantity());
                    itemOrder.setUserOrder(order);
                    itemOrder.setProduct(product);

                    return itemOrder;
                })
                .collect(Collectors.toList());
    }
}
