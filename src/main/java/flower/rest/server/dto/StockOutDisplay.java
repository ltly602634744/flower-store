package flower.rest.server.dto;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutDisplay {

    private LocalDateTime stockOutTime;
    private Employee stockOutEmployee;
    private Item stockOutItem;
    private int stockOutQuantity;
    private double stockOutPrice;
    private String stockOutCategory;

    public StockOutDisplay(LocalDateTime stockOutTime, Employee stockOutEmployee, Item stockOutItem,
                       int stockOutQuantity, double stockOutPrice, String stockOutCategory) {
        this.stockOutTime = stockOutTime;
        this.stockOutEmployee = stockOutEmployee;
        this.stockOutItem = stockOutItem;
        this.stockOutQuantity = stockOutQuantity;
        this.stockOutPrice = stockOutPrice;
        this.stockOutCategory = stockOutCategory;
    }
}