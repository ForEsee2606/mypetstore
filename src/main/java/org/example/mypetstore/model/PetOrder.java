package org.example.mypetstore.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PetOrder {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String status;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
}
