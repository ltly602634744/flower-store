package flower.rest.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MEMBERSHIP")
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private int membershipId;

    @Column(name = "membership_name")
    private String membershipName;

    @Column(name = "membership_starting_time")
    private LocalDateTime membershipStartingTime;

    @Column(name = "membership_point")
    private int membershipPoint;

    @Column(name = "membership_telephone")
    private String membershipTelephone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "anniversary_membership_id")//foreign key
    private List<Anniversary> anniversaries;


}
