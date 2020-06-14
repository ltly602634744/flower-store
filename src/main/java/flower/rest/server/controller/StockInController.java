package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.*;
import flower.rest.server.dto.StockInDTO;
import flower.rest.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/stockIns1")
public class StockInController {

    private StockInRepository stockInRepository;
    private StockRepository stockRepository;
    private EmployeeRepository employeeRepository;
    private VendorRepository vendorRepository;
    private ItemRepository itemRepository;


    @Autowired
    public StockInController(StockInRepository stockInRepository, StockRepository stockRepository, EmployeeRepository employeeRepository, VendorRepository vendorRepository, ItemRepository itemRepository) {
        this.stockInRepository = stockInRepository;
        this.stockRepository = stockRepository;
        this.employeeRepository = employeeRepository;
        this.vendorRepository = vendorRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<StockIn> findAll(){
        return ControllerTools.findAll(stockInRepository);
    }

    @GetMapping("/{stockInId}")
    public StockIn findStockInById(@PathVariable int stockInId){
        return ControllerTools.findById(stockInRepository, stockInId);
    }

    private StockIn dto2StockIn(StockIn theStockIn, StockInDTO theStockInDTO) {

        double stockInPrice = theStockInDTO.getStockInPrice();
        int stockInQuantity = theStockInDTO.getStockInQuantity();
        double stockInTotalPrice = stockInPrice * stockInQuantity;
        Employee stockInEmployee = null;
        Item stockInItem = null;
        Vendor stockInVendor = null;

        stockInEmployee = employeeRepository.findById(theStockInDTO.getStockInEmployeeId())
                .orElseThrow( ()-> new RuntimeException("Employee " + theStockInDTO.getStockInEmployeeId() + " is not exist"));

        stockInItem = itemRepository.findById(theStockInDTO.getStockInItemId())
                .orElseThrow( ()-> new RuntimeException("Item " + theStockInDTO.getStockInItemId() + " is not exist"));

        stockInVendor = vendorRepository.findById(theStockInDTO.getStockInVendorId())
                .orElseThrow( ()-> new RuntimeException("Vendor " + theStockInDTO.getStockInVendorId() + " is not exist"));

        theStockIn.setStockInEmployee(stockInEmployee);
        theStockIn.setStockInItem(stockInItem);
        theStockIn.setStockInPrice(stockInPrice);
        theStockIn.setStockInQuantity(stockInQuantity);
        theStockIn.setStockInVendor(stockInVendor);
        theStockIn.setStockInTotalPrice(stockInTotalPrice);

        Stock theStock = stockRepository.findById(theStockInDTO.getStockInItemId())
//                .orElseThrow(()->new RuntimeException("Item id " + theStockInDTO.getStockInItemId() + " is not exist"));
                    .orElseGet(Stock::new);

        if(theStockIn.getStockInId() == 0){
            theStock.setItem(stockInItem);
            stockRepository.save(theStock.addQuantity(stockInQuantity));
        }else{
            stockRepository.save(theStock.addQuantity(stockInQuantity - stockInRepository.findById(theStockIn.getStockInId()).get().getStockInQuantity()));
        }

        stockInRepository.save(theStockIn);

        return theStockIn ;
    }

    @PostMapping
    public StockIn createStockIn(@RequestBody StockInDTO theStockInDTO){
        StockIn theStockIn = new StockIn();
        theStockIn.setStockInId(0);
        return this.dto2StockIn(theStockIn, theStockInDTO) ;
    }

    @PutMapping("/{stockInId}")
    public StockIn updateStockIn(@PathVariable int stockInId,
                                 @RequestBody StockInDTO theStockInDTO){
        StockIn theStockIn = new StockIn();
        theStockIn.setStockInId(stockInId);
        return dto2StockIn(theStockIn, theStockInDTO);
    }

    @DeleteMapping("/{stockInId}")
    public String deleteStockIn(@PathVariable int stockInId){

        StockIn theStockIn = null;
        try {
            theStockIn = stockInRepository.findById(stockInId)
                    .orElseThrow(()->new Exception(stockInId + " not exist"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stock theStock = stockRepository.findByItemId(theStockIn.getStockInItem().getItemId());
        if (theStock.getStockQuantity() < theStockIn.getStockInQuantity()){
            throw new RuntimeException("Delete failed, the stock quantity is not enough!");
        }
        theStock.addQuantity(-theStockIn.getStockInQuantity());
        stockRepository.save(theStock);

        stockInRepository.deleteById(stockInId);

        return "Deleted stockIn " + stockInId;

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}


