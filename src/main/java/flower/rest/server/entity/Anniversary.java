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
    private int membershipId;

    @Column(name = "anniversary_name")
    private String membershipName;

    @Column(name = "anniversary_date")
    private LocalDate membershipDate;

    @Column(name = "anniversary_prefer")
    private String anniversaryPrefer;
}
