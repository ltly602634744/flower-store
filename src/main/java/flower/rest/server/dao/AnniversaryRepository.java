package flower.rest.server.dao;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Membership;
import flower.rest.server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface AnniversaryRepository extends JpaRepository<Anniversary, Integer> {

    List<Anniversary> findByAnniversaryMembershipId(Integer membershipId);

    List<Anniversary> findByAnniversaryNameContainingOrAnniversaryPreferContaining(String name, String prefer);
}
