package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/employees1")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    public List<Employee> findAll(){
        return this.employeeRepository.findAll();
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable int employeeId){
        Optional<Employee> result = this.employeeRepository.findById(employeeId);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new RuntimeException("Employee is not exist");
        }
    }

    @GetMapping("/fuzzySearch")
    public List<Employee> fuzzySearch(@RequestParam("content") String content){
        String decodedContent = ControllerTools.decodeSearchContent(content);
        return employeeRepository
                .findByEmployeeNameContainingOrEmployeeTelephoneContainingOrEmployeeStatusContaining(decodedContent, decodedContent, decodedContent);
    }


    @PostMapping()
    public Employee addEmployee(@RequestBody Employee theEmployee
//                            BindingResult bindingResult
    ){

//        if(bindingResult.hasErrors()){
//            System.out.println(bindingResult.getAllErrors());
//        }

        theEmployee.setEmployeeId(0);

        this.employeeRepository.save(theEmployee);

        return theEmployee;
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable int employeeId,
                             @RequestBody Employee theEmployee){

        theEmployee.setEmployeeId(employeeId);
        this.employeeRepository.save(theEmployee);

        return theEmployee;
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Optional<Employee> result = this.employeeRepository.findById(employeeId);

        if(result.isPresent()){
           this.employeeRepository.deleteById(employeeId);
            return "deleted item: " + result.get();
        }else{
            throw new RuntimeException("Item is not exist");
        }
    }
}
