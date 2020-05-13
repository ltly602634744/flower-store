package flower.rest.server.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "VENDOR")
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private int id;

    @Column(name = "vendor_name")
    private String name;

    @Column(name = "vendor_contact")
    private String contact;

    @Column(name = "vendor_telephone")
    private String phone;

    public void copy(Vendor vendor){
        this.name = vendor.name;
        this.contact = vendor.contact;
        this.phone = vendor.phone;
    }

}
