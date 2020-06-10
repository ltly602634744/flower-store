package flower.rest.server.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table( name = "TRANSACTION")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "transaction_time")
    @CreationTimestamp
    private LocalDateTime transactionTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "transaction_employee_id")
    private Employee transactionEmployee;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_out_transaction_id")
    private List<StockOut> transactionStockOuts;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "transaction_membership_id")
    private Membership transactionMembership;

    @Column(name = "transaction_amount")
    private LocalDateTime transactionAmount;


}
