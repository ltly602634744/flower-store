package flower.rest.server.dao;

import flower.rest.server.entity.Membership;
import flower.rest.server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
public interface MembershipRepository extends JpaRepository<Membership, Integer> {

//    List<Membership> findByMembershipNameContaining(@RequestParam("name") String name);
//
//    List<Vendor> findByVendorContactContaining(@RequestParam("name") String contact);

    @RestResource(rel="fuzzySearch", path = "fuzzySearch")
    List<Membership> findByMembershipNameContainingOrMembershipSexContainingOrMembershipTelephoneContaining(String name, String sex, String telephone);

    List<Membership> findByMembershipPointIsGreaterThan(@RequestParam("threshold") int threshold);

    List<Membership> findByMembershipPointIsLessThan(int threshold);

    List<Membership> findByMembershipStartingTimeIsBefore(LocalDate time);

    List<Membership> findByMembershipStartingTimeIsAfter(LocalDate time);

}
