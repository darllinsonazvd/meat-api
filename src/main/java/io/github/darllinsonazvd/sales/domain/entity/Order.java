package io.github.darllinsonazvd.sales.domain.entity;

import io.github.darllinsonazvd.sales.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate orderDate;

    @Column(precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "userOrder")
    private List<ItemOrder> items;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
