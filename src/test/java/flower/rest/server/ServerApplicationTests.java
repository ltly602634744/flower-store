package flower.rest.server;

import flower.rest.server.dao.MembershipRepository;
import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Membership;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    void contextLoads() {


    }

//    @Test
//    void testGetMemberships() {
//        System.out.println(this.membershipRepository.findAll());
//
//    }

//    @Test
//    void testCreateMemberships() {
////        Membership theMembership = new Membership();
////        theMembership.setMembershipId(4);
////        theMembership.setMembershipName("lisi");
////        theMembership.setMembershipSex(true);
////        theMembership.setMembershipPoint(10);
////        theMembership.setMembershipTelephone("123");
//////        theMembership.setMembershipStartingTime();
//        Membership theMembership = this.membershipRepository.getOne(9);
//
//        Anniversary theAnniversary = new Anniversary();
//        theAnniversary.setMembershipDate(LocalDate.now());
//        theAnniversary.setMembershipName("shengri");
//        theMembership.getAnniversaries().add(theAnniversary);
////        this.membershipRepository.save(theMembership);
//        System.out.println(this.membershipRepository.findByMembershipNameContaining("lisi"));
//
//    }

}
