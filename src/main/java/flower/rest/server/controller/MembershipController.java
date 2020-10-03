package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.AnniversaryRepository;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.MembershipRepository;
import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/memberships1")
public class MembershipController {

    private MembershipRepository membershipRepository;
    private AnniversaryRepository anniversaryRepository;

    @Autowired
    public MembershipController(MembershipRepository membershipRepository,
                                AnniversaryRepository anniversaryRepository){
        this.membershipRepository = membershipRepository;
        this.anniversaryRepository = anniversaryRepository;
    }

    @GetMapping()
    public List<Membership> findAll(){
        return ControllerTools.findAll(membershipRepository);
    }

    @GetMapping("/{membershipId}")
    public Membership findMembershipById(@PathVariable int membershipId){
        return ControllerTools.findById(membershipRepository, membershipId);
    }

    @GetMapping("/fuzzySearch")
    public List<Membership> fuzzySearch(@RequestParam("content") String content){
        String decodedContent = ControllerTools.decodeSearchContent(content);
        return membershipRepository
                .findByMembershipNameContainingOrMembershipSexContainingOrMembershipTelephoneContaining(decodedContent, decodedContent, decodedContent);
    }

    @PostMapping()
    public Membership addMembership(@RequestBody Membership theMembership){

        theMembership.setMembershipId(0);

        return ControllerTools.saveValue(membershipRepository, theMembership);
    }

    @PutMapping("/{membershipId}")
    public Membership updateMembership(@PathVariable int membershipId,
                             @RequestBody Membership theMembership){

        Membership originalMembership = membershipRepository.findById(membershipId).get();

        theMembership.setMembershipId(membershipId);
        theMembership.setMembershipStartingTime(originalMembership.getMembershipStartingTime());
        theMembership.setAnniversaries(originalMembership.getAnniversaries());

        return ControllerTools.saveValue(membershipRepository, theMembership);
    }

    @DeleteMapping("/{membershipId}")
    public String deleteMembership(@PathVariable int membershipId){
       return ControllerTools.deleteById(membershipRepository, membershipId);
    }

    @GetMapping("/{membershipId}/anniversaries")
    public List<Anniversary> findAnniversariesByMembershipId(@PathVariable int membershipId){
        return anniversaryRepository.findByAnniversaryMembershipId(membershipId);
    }

    @PostMapping("/{membershipId}/anniversaries")
    public Anniversary addAnniversaryByMembershipId(@PathVariable int membershipId,
                                                   @RequestBody Anniversary anniversary){
       anniversary.setAnniversaryId(0);
       anniversary.setAnniversaryMembershipId(membershipId);

       return ControllerTools.saveValue(anniversaryRepository, anniversary);
    }

    @PutMapping("/{membershipId}/anniversaries/{anniversaryId}")
    public Anniversary updateAnniversaryByMembershipId(@PathVariable int membershipId,
                                                       @PathVariable int anniversaryId, @RequestBody Anniversary anniversary){

        anniversary.setAnniversaryId(anniversaryId);
        anniversary.setAnniversaryMembershipId(membershipId);

        return ControllerTools.saveValue(anniversaryRepository, anniversary);
    }

    @DeleteMapping("/{membershipId}/anniversaries/{anniversaryId}")
    public String deleteAnniversaryByMembershipId(@PathVariable int membershipId,
                                                  @PathVariable int anniversaryId){
        return ControllerTools.deleteById(anniversaryRepository, anniversaryId);
    }

}
