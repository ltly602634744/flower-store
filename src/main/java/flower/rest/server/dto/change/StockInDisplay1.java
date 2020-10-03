package flower.rest.server.dto.change;

import flower.rest.server.entity.StockIn;
import lombok.Data;

//@JsonIgnoreProperties(value = {"stockIn"})
@Data
public class StockInDisplay1 extends StockIn{

    private String stockInItemName;

    private String stockInVendorName;

    public StockInDisplay1(StockInDisplay1 s) {
        this.stockInItemName = super.getStockInItem().getItemName();
        this.stockInVendorName = super.getStockInVendor().getVendorName();
    }
}
