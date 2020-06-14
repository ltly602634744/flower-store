package flower.rest.server;

import flower.rest.server.dao.LossRepository;
import flower.rest.server.dao.StockOutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockOutTest {

    @Autowired
    private StockOutRepository stockOutRepository;

    @Test
    public void test1(){
        stockOutRepository.deleteById(29);
    }
}
