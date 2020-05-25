package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.AnniversaryRepository;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/anniversaries1")
public class AnniversaryController {

    private AnniversaryRepository anniversaryRepository;

    @Autowired
    public AnniversaryController(AnniversaryRepository anniversaryRepository){
        this.anniversaryRepository = anniversaryRepository;
    }

    @GetMapping()
    public List<Anniversary> findAll(){
        return ControllerTools.findAll(anniversaryRepository);
    }

    @GetMapping("/{anniversaryId}")
    public Anniversary findAnniversaryById(@PathVariable int anniversaryId){
        return ControllerTools.findById(anniversaryRepository, anniversaryId);
    }

    @GetMapping("/fuzzySearch")
    public List<Anniversary> fuzzySearch(@RequestParam("content") String content){
        return anniversaryRepository
                .findByAnniversaryNameContainingOrAnniversaryPreferContaining(content, content);
    }


    @PostMapping()
    public Anniversary addAnniversary(@RequestBody Anniversary theAnniversary){

        theAnniversary.setAnniversaryId(0);

        this.anniversaryRepository.save(theAnniversary);

        return theAnniversary;
    }

    @PutMapping("/{anniversaryId}")
    public Anniversary updateAnniversary(@PathVariable int anniversaryId,
                             @RequestBody Anniversary theAnniversary){

        theAnniversary.setAnniversaryId(anniversaryId);
        this.anniversaryRepository.save(theAnniversary);

        return theAnniversary;
    }

    @DeleteMapping("/{anniversaryId}")
    public String deleteAnniversary(@PathVariable int anniversaryId){
      return ControllerTools.deleteById(anniversaryRepository, anniversaryId);
    }
}
