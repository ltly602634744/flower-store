package flower.rest.server.dto.change;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.StockIn;
import flower.rest.server.entity.Vendor;
import lombok.Data;

import java.time.LocalDateTime;

//@JsonIgnoreProperties(value = {"stockIn"})
@Data
public class StockInDisplay {

//    private StockIn stockIn;

    private int stockInId;

    private LocalDateTime stockInTime;

    private int stockInQuantity;

    private double stockInPrice;

    private double stockInTotalPrice;

    private Employee stockInEmployee;

    private Item stockInItem;

    private Vendor stockInVendor;

    private String stockInItemName;

    private String stockInVendorName;

    public StockInDisplay stockIn2StockInDisplay(StockIn stockIn) {
        this.stockInId = stockIn.getStockInId();
        this.stockInTime = stockIn.getStockInTime();
        this.stockInQuantity = stockIn.getStockInQuantity();
        this.stockInPrice = stockIn.getStockInPrice();
        this.stockInEmployee = stockIn.getStockInEmployee();
        this.stockInItem = stockIn.getStockInItem();
        this.stockInVendor = stockIn.getStockInVendor();
        this.stockInItemName = stockIn.getStockInItem().getItemName();
        this.stockInVendorName = stockIn.getStockInVendor().getVendorName();
        return this;
    }
}
