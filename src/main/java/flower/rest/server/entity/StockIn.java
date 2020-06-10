package flower.rest.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForLocalDateTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK_IN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_in_id")
    private int stockInId;

//    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "stock_in_time")
    private LocalDateTime stockInTime;

    @Column(name = "stock_in_quantity")
    private int stockInQuantity;

    @Column(name = "stock_in_price")
    private double stockInPrice;

    @Column(name = "stock_in_total_price")
    private double stockInTotalPrice;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_employee_id")//foreign key
    private Employee stockInEmployee;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_item_id")//foreign key
    private Item stockInItem;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_vendor_id")//foreign key
    private Vendor stockInVendor;

}
