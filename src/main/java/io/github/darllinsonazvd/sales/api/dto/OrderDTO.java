package io.github.darllinsonazvd.sales.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Integer customerId;
    private BigDecimal total;
    private List<ItemOrderDTO> items;
}
