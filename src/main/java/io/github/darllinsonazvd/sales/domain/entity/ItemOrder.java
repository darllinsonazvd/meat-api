package io.github.darllinsonazvd.sales.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_item_order")
@Data
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order userOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
