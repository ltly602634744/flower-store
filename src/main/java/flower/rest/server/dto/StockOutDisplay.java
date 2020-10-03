package flower.rest.server.dto;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.StockOut;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockOutDisplay {

    private int stockOutId;
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

    public StockOutDisplay(int stockOutId, LocalDateTime stockOutTime, Employee stockOutEmployee, Item stockOutItem,
                           int stockOutQuantity, double stockOutPrice, String stockOutCategory) {
        this.stockOutId = stockOutId;
        this.stockOutTime = stockOutTime;
        this.stockOutEmployee = stockOutEmployee;
        this.stockOutItem = stockOutItem;
        this.stockOutQuantity = stockOutQuantity;
        this.stockOutPrice = stockOutPrice;
        this.stockOutCategory = stockOutCategory;
    }

//    public StockOutToStockOutDisplay(StockOut stockOut){
//        StockOutDisplay stockOutDisplay = new StockOutDisplay();
//        stockOutDisplay.setStockOutId(stockOut.getStockOutId());
//        stockOutDisplay.setStockOutEmployee(stockOut.get());
//    }
}