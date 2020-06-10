package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.PriceRepository;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/prices1")
public class PriceController {

    private PriceRepository priceRepository;
    private ItemRepository itemRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public PriceController(PriceRepository priceRepository, ItemRepository itemRepository, EmployeeRepository employeeRepository) {
        this.priceRepository = priceRepository;
        this.itemRepository = itemRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    public List<Price> findAll(){
        return ControllerTools.findAll(priceRepository);
    }

//    @GetMapping("/searchByItemId")
//    public Price searchByItemId(@RequestParam int itemId){
//        List<Price> prices = priceRepository.findByPriceItemId(itemId);
//        return prices.get(prices.size()-1);
//    }

//    @PostMapping()
//    public Price addPrice(@RequestBody Price thePrice,
//                          @RequestParam int itemId,
//                          @RequestParam int employeeId
//    ){
//        thePrice.setPriceId(0);
//
//        Optional<Item> itemOptional = itemRepository.findById(itemId);
//        Item item = itemOptional.orElseThrow();
//
//        thePrice.setPriceItem(item);
//
//        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
//        Employee employee = employeeOptional.orElseThrow();
//
//        thePrice.setEmployee(employee);
//
//        thePrice.setPriceFinal(thePrice.getPrice() * thePrice.getDiscount());
//
//        System.out.println(thePrice);
//
//        priceRepository.save(thePrice);
//
//        return thePrice;
//    }


}
