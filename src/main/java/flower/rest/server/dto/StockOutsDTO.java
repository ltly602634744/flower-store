package flower.rest.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockOutsDTO {

    protected int employeeId;

    protected List<Integer> itemIds;

    protected List<Integer> itemQuantities;
}
