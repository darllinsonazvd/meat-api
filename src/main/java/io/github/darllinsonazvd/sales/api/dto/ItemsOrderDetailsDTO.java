package io.github.darllinsonazvd.sales.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemsOrderDetailsDTO {
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
