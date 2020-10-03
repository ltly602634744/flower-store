package flower.rest.server.dto;

import lombok.Data;

@Data
public class TransactionDTO extends StockOutDTO{
    private int transactionId;

    private int membershipId;

    private double transactionAmount;
}
