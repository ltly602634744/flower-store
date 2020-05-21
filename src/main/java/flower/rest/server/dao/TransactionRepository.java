package flower.rest.server.dao;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    List<Vendor> findByVendorNameContaining(@RequestParam("name") String name);
//
//    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

}
