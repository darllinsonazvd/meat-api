package io.github.darllinsonazvd.sales.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer id) {
        super("Order not found for id: " + id);
    }
}
