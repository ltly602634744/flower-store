package flower.rest.server.ErrorResponse;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private int status;

    private String message;

    private long timeStamp;



//    public StockOutErrorResponse(int status, String message, long timeStamp) {
//        this.status = status;
//        this.message = message;
//        this.timeStamp = timeStamp;
//    }
}
