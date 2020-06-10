package flower.rest.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM")
@Data
@JsonIgnoreProperties(value = {"prices"})
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

//    @OneTo(cascade = CascadeType.ALL)
//    @JoinColumn(name = "price_item_id")
//    private List<Price> itemPrices;

    @Column(name = "item_location")
    private String itemLocation;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "price_item_id")//foreign key
//    private List<Price> prices;

//    public void addVendor(Vendor vendor){
//        if(this.vendors == null){
//            this.vendors = new ArrayList<>();
//        }
//        this.vendors.add(vendor);
//    }
}
