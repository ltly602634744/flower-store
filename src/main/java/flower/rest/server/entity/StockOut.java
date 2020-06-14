package flower.rest.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "STOCK_OUT")
//@JsonIgnoreProperties(value={"stockOutLoss"})
@Data
public class StockOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_out_id")
    private int stockOutId;

    @Column(name = "stock_out_quantity")
    private int stockOutQuantity;

//    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "stock_out_loss_id")
//    private Loss stockOutLoss;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "stock_out_item_id")
    private Item stockOutItem;

    @Column(name = "stock_out_final_price")
    private double priceFinal;

//    public StockOut(){}


}
