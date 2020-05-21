package flower.rest.server.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENDOR")
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private int vendorId;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_contact")
    private String vendorContact;

    @Column(name = "vendor_telephone")
    private String vendorTelephone;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
//    })
//    @JoinTable(
//            name="ITEM-VENDOR",
//            joinColumns = @JoinColumn(name="vendor_id"),
//            inverseJoinColumns = @JoinColumn(name="item_id")
//    )
//    private List<Item> items;

//    public void copy(Vendor vendor){
//        this.name = vendor.name;
//        this.contact = vendor.contact;
//        this.phone = vendor.phone;
//    }

//    public void addItem(Item item){
//        if(this.items == null){
//            this.items = new ArrayList<>();
//        }
//        this.items.add(item);
//    }

}
