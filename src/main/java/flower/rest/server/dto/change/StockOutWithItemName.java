package flower.rest.server.dto.change;

import flower.rest.server.entity.Item;
import flower.rest.server.entity.StockOut;
import lombok.Data;

@Data
public class StockOutWithItemName {

    private int stockOutId;

    private int stockOutQuantity;

    private Item stockOutItem;

    private double priceFinal;

    private String stockOutItemName;

    public StockOutWithItemName StockOut2StockOutWithItemName(StockOut stockOut){
        this.stockOutId = stockOut.getStockOutId();
        this.stockOutQuantity = stockOut.getStockOutQuantity();
        this.stockOutItem = stockOut.getStockOutItem();
        this.priceFinal = stockOut.getPriceFinal();
        this.stockOutItemName = stockOut.getStockOutItem().getItemName();
        return this;
    }
}
