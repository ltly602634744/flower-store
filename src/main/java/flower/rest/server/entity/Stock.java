package flower.rest.server.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int stockId;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @OneToOne(cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "stock_item_id")
    private Item item;
}
