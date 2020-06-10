package flower.rest.server.dto;

import lombok.Data;

@Data
public class StockInDTO{
    private int stockInQuantity;

    private double stockInPrice;

    private int stockInEmployeeId;

    private int stockInItemId;

    private int stockInVendorId;
}
