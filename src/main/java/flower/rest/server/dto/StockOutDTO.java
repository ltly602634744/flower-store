package flower.rest.server.dto;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutDTO {

    private LocalDateTime StockOutTime;
    private Employee StockOutEmployee;
    private Item StockOutItem;
    private int StockOutQuantity;
    private String StockOutCategory;

    public StockOutDTO(LocalDateTime stockOutTime, Employee stockOutEmployee,
                       Item stockOutItem, int stockOutQuantity, String stockOutCategory) {
        StockOutTime = stockOutTime;
        StockOutEmployee = stockOutEmployee;
        StockOutItem = stockOutItem;
        StockOutQuantity = stockOutQuantity;
        StockOutCategory = stockOutCategory;
    }
}