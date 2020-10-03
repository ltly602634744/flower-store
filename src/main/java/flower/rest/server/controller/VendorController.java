package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.StockInRepository;
import flower.rest.server.dao.VendorRepository;
import flower.rest.server.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/vendors1")
public class VendorController {

    private VendorRepository vendorRepository;

    @Autowired
    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping()
    public List<Vendor> findAll(){
        return this.vendorRepository.findAll();
    }

    @GetMapping("/{vendorId}")
    public Vendor findById(@PathVariable int vendorId){
        Optional<Vendor> result = this.vendorRepository.findById(vendorId);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new RuntimeException("Vendor is not exist");
        }
    }

    @GetMapping("/fuzzySearch")
    public List<Vendor> fuzzySearch(@RequestParam("content") String content) {

        String decodedContent = ControllerTools.decodeSearchContent(content);
        return vendorRepository
                .findByVendorNameContainingOrVendorContactContainingOrVendorTelephoneContaining(decodedContent,decodedContent, decodedContent);


//        return Stream.of(vendorRepository.findByVendorContactContaining(content),
//                        vendorRepository.findByVendorNameContaining(content),
//                        vendorRepository.findByVendorTelephoneContaining(content))
//                    .flatMap(Collection::stream)
//                    .distinct()
//                    .collect(Collectors.toList());


    }


    @PostMapping()
    public Vendor addVendor(@RequestBody Vendor theVendor){

        theVendor.setVendorId(0);

        if (vendorRepository.findByVendorName(theVendor.getVendorName()) !=null){
            throw new RuntimeException("花店已存在");
        }

        this.vendorRepository.save(theVendor);

        return theVendor;
    }

    @PutMapping("/{vendorId}")
    public Vendor updateVendor(@PathVariable int vendorId,
            @RequestBody Vendor theVendor){

        theVendor.setVendorId(vendorId);
        this.vendorRepository.save(theVendor);

        return theVendor;
    }

    @DeleteMapping("/{vendorId}")
    public String deleteVendor(@PathVariable int vendorId){
        Optional<Vendor> result = this.vendorRepository.findById(vendorId);

        if(result.isPresent()){
           this.vendorRepository.deleteById(vendorId);
            return "deleted vendor: " + result.get();
        }else{
            throw new RuntimeException("Vendor is not exist");
        }

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }
}
