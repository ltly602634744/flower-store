package flower.rest.server.dao;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Loss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface LossRepository extends JpaRepository<Loss, Integer> {

//    List<Vendor> findByVendorNameContaining(@RequestParam("name") String name);
//
//    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

}
