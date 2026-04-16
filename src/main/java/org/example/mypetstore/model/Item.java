package org.example.mypetstore.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Item {
    private Long id;
    private String name;
    private Long productTypeId;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
}
