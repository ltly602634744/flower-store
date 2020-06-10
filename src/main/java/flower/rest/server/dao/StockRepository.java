package flower.rest.server.dao;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "select s from Stock s where s.item.itemName LIKE %:itemName%")
    List<Stock> findByItemName(@RequestParam String itemName);

    @Query(value = "select s from Stock s where s.item.itemId = :itemId")
    Stock findByItemId(@RequestParam int itemId);

//    List<Vendor> findByVendorNameContaining(@RequestParam("name") String name);
//
//    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

}
