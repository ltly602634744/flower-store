package flower.rest.server.dto;

import lombok.Data;

@Data
public class StockInDTO{
    private int stockInQuantity;

    private double stockInPrice;

    private int employeeId;

    private String stockInItemName;

    private String stockInVendorName;
}
