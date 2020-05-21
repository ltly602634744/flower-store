package flower.rest.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "item_name")
    private String itemName;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
//    })
//    @JoinTable(
//            name="ITEM-VENDOR",
//            joinColumns = @JoinColumn(name="item_id"),
//            inverseJoinColumns = @JoinColumn(name="vendor_id")
//    )
//    private List<Vendor> vendors;

    @OneToOne(mappedBy = "priceItem", cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    private Price price;

    @Column(name = "item_location")
    private String itemLocation;

//    public void addVendor(Vendor vendor){
//        if(this.vendors == null){
//            this.vendors = new ArrayList<>();
//        }
//        this.vendors.add(vendor);
//    }
}
