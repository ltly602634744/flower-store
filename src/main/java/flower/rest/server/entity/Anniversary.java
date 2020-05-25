package flower.rest.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ANNIVERSARY")
@Data
public class Anniversary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anniversary_id")
    private int anniversaryId;

    @Column(name = "anniversary_name")
    private String anniversaryName;

    @Column(name = "anniversary_date")
    private LocalDate anniversaryDate;

    @Column(name = "anniversary_prefer")
    private String anniversaryPrefer;

//    @ManyToOne(cascade = {
//            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
//    })
//    @JoinColumn(name = "anniversary_membership_id")//foreign key
//    private Membership membership;

    @Column(name = "anniversary_membership_id")
    private int anniversaryMembershipId;

}
