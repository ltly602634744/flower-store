package flower.rest.server.dao;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface PriceRepository extends JpaRepository<Price, Integer> {

//    List<Vendor> findByVendorNameContaining(@RequestParam("name") String name);
//
//    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

//    @Query(value = "select p from Price p where p.priceItem = :item")
//    List<Price> findByPriceItem(Item item);

    @Query(value = "select p from Price p where p.priceItem.itemId = :itemId")
    List<Price> findByPriceItemId(@RequestParam("itemId") int itemId);

}
