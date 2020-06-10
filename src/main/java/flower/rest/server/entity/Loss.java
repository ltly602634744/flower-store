package flower.rest.server.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table( name = "LOSS")
@Data
public class Loss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loss_id")
    private int lossId;

    @Column(name = "loss_time")
    @CreationTimestamp
    private LocalDateTime lossTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "loss_employee_id")
    private Employee lossEmployee;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_out_loss_id")
    private List<StockOut> lossStockOuts;
}
