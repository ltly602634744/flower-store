package flower.rest.server;

import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.PriceRepository;
import flower.rest.server.dao.VendorRepository;
import flower.rest.server.entity.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class PricesTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ItemRepository itemRepository;

//    @Test
//    public void testSearchByItem(){
//        List<Price> result = priceRepository.findByPriceItem(itemRepository.findById(2).get());
//        System.out.println(result);
//    }

//    @Test
//    public void testSearchByItemId(){
//        List<Price> result = priceRepository.findByPriceItemId(2);
//        System.out.println(result);
//    }


}
