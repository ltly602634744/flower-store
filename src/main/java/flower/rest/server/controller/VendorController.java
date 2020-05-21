package flower.rest.server.controller;

import flower.rest.server.dao.VendorRepository;
import flower.rest.server.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class VendorController {

    private VendorRepository vendorRepository;

    @Autowired
    public VendorController(VendorRepository vendorRepository){
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/vendors1")
    public List<Vendor> findAll(){
        return this.vendorRepository.findAll();
    }

    @GetMapping("/vendors1/{vendorId}")
    public Vendor findById(@PathVariable int vendorId){
        Optional<Vendor> result = this.vendorRepository.findById(vendorId);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new RuntimeException("Vendor is not exist");
        }
    }


    @PostMapping("/vendors1")
    public Vendor addVendor(@RequestBody Vendor theVendor
//                            BindingResult bindingResult
    ){

//        if(bindingResult.hasErrors()){
//            System.out.println(bindingResult.getAllErrors());
//        }

        theVendor.setVendorId(0);

        this.vendorRepository.save(theVendor);

        return theVendor;
    }

    @PutMapping("/vendors1/{vendorId}")
    public Vendor updateVendor(@PathVariable int vendorId,
            @RequestBody Vendor theVendor){

        theVendor.setVendorId(vendorId);
        this.vendorRepository.save(theVendor);

        return theVendor;
    }

    @DeleteMapping("/vendors1/{vendorId}")
    public String deleteVendor(@PathVariable int vendorId){
        Optional<Vendor> result = this.vendorRepository.findById(vendorId);

        if(result.isPresent()){
           this.vendorRepository.deleteById(vendorId);
            return "deleted vendor: " + result.get();
        }else{
            throw new RuntimeException("Vendor is not exist");
        }

    }
}
