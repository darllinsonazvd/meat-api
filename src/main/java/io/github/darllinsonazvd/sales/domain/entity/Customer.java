package io.github.darllinsonazvd.sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "tb_customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
}
