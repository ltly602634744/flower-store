package flower.rest.server.dto;

import lombok.Data;

@Data
public class TransactionDTO extends StockOutsDTO{
    private int transactionId;

    private int membershipId;

    private double transactionAmount;
}
