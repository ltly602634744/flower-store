package flower.rest.server.dao;

import flower.rest.server.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    List<Vendor> findByVendorNameContaining(@RequestParam("name") String name);

    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

}
