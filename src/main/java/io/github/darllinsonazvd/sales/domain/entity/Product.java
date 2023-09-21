package io.github.darllinsonazvd.sales.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    @Column(precision = 20, scale = 2)
    private BigDecimal price;
}
