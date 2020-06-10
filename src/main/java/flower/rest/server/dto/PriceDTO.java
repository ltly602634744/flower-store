package flower.rest.server.dto;

import lombok.Data;

@Data
public class PriceDTO{

    private int employeeId;
    private double price;
    private double discount;
    private double priceMembership;

}