package flower.rest.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockOutDTO {

    protected int employeeId;

    protected int stockOutId;

    protected String itemName;

    protected int itemQuantity;

    protected int finalUnitPrice;
}
