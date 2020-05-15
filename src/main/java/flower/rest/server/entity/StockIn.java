package flower.rest.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int stock_in_id;

    @Column(name = "stock_in_time")
    private LocalDateTime stock_in_time;

    @Column(name = "stock_in_quantity")
    private int stock_in_quantity;

    @Column(name = "stock_in_price")
    private double stock_in_price;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_employee_id")//foreign key
    private Employee stock_in_employee;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_item_id")//foreign key
    private Item stock_in_item;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "stock_in_vendor_id")//foreign key
    private Vendor stock_in_vendor;

}
