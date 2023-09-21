package io.github.darllinsonazvd.sales.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDTO {
    private Integer id;
    private String customerName;
    private String cpf;
    private BigDecimal total;
    private String dateTime;
    private String status;
    private List<ItemsOrderDetailsDTO> items;

}
