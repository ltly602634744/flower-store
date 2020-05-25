package flower.rest.server.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBERSHIP")
@Data
//@ToString
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private int membershipId;

    @Column(name = "membership_name")
    private String membershipName;

    @Column(name = "membership_sex")
    private String membershipSex;

    @Column(name = "membership_starting_time")
    @CreationTimestamp
    private LocalDate membershipStartingTime;

    @Column(name = "membership_point")
    private int membershipPoint;

    @Column(name = "membership_telephone")
    private String membershipTelephone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY
//            , mappedBy ="membership", fetch = FetchType.LAZY
    )
    @JoinColumn(name = "anniversary_membership_id")//foreign key
    private List<Anniversary> anniversaries;



    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipName='" + membershipName + '\'' +
                ", membershipSex=" + membershipSex +
                ", membershipStartingTime=" + membershipStartingTime +
                ", membershipPoint=" + membershipPoint +
                ", membershipTelephone='" + membershipTelephone + '\'' +
                ", anniversaries=" + anniversaries +
                '}';
    }
}
