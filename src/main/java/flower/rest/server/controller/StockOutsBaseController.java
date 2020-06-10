package flower.rest.server.controller;

import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.StockOutRepository;
import flower.rest.server.dao.StockRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Component
@Data
public class StockOutsBaseController {

    protected StockOutRepository stockOutRepository;
    protected StockRepository stockRepository;
    protected EmployeeRepository employeeRepository;
    protected ItemRepository itemRepository;

    @Autowired
    public void setStockOutRepository(StockOutRepository stockOutRepository) {
        this.stockOutRepository = stockOutRepository;
    }

    @Autowired
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
