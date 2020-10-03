package flower.rest.server.dto.change;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Loss;
import flower.rest.server.entity.StockOut;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LossDisplay{
    private int lossId;

    private LocalDateTime lossTime;

    private Employee lossEmployee;

    private List<StockOut> lossStockOuts;
    private String employeeName;

    public LossDisplay loss2LossDisplay(Loss loss){
        this.lossId = loss.getLossId();
        this.lossTime = loss.getLossTime();
        this.lossEmployee = loss.getLossEmployee();
        this.lossStockOuts = loss.getLossStockOuts();
        this.employeeName = loss.getLossEmployee().getEmployeeName();
        return this;
    }
}
