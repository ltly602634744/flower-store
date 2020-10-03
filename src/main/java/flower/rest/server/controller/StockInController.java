package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.*;
import flower.rest.server.dto.StockInDTO;
import flower.rest.server.dto.change.StockInDisplay;
import flower.rest.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

//    @GetMapping
//    public List<StockIn> findAll(){
//        return ControllerTools.findAll(stockInRepository);
//    }

//    @GetMapping("/{stockInId}")
//    public StockIn findStockInById(@PathVariable int stockInId){
//        return ControllerTools.findById(stockInRepository, stockInId);
//    }


//    @GetMapping("/fuzzySearch")
//    public List<StockIn> fuzzySearch(@RequestParam("content") String content){
//        String decodedContent = ControllerTools.decodeSearchContent(content);
//        return stockInRepository.findByNames(content);
//    }
    @GetMapping
    public List<StockInDisplay> findAll(){
        return ControllerTools.findAll(stockInRepository).stream()
                .map(x->{
                    StockInDisplay sid = new StockInDisplay();
                    return sid.stockIn2StockInDisplay(x);
                }).collect(Collectors.toList());
    }

    @GetMapping("/{stockInId}")
    public StockInDisplay findStockInById(@PathVariable int stockInId){
        StockInDisplay sid = new StockInDisplay();
        return sid.stockIn2StockInDisplay(ControllerTools.findById(stockInRepository, stockInId));
    }


    @GetMapping("/fuzzySearch")
    public List<StockInDisplay> fuzzySearch(@RequestParam("content") String content){
        String decodedContent = ControllerTools.decodeSearchContent(content);
        return stockInRepository.findByNames(content).stream()
                .map(x->{
                    StockInDisplay sid = new StockInDisplay();
                    return sid.stockIn2StockInDisplay(x);
                }).collect(Collectors.toList());
    }



    private StockIn dto2StockIn(StockIn theStockIn, StockInDTO theStockInDTO) throws IncorrectResultSizeDataAccessException {

        double stockInPrice = theStockInDTO.getStockInPrice();
        int stockInQuantity = theStockInDTO.getStockInQuantity();
        double stockInTotalPrice = stockInPrice * stockInQuantity;
        Employee stockInEmployee = null;
        Item stockInItem = null;
        Vendor stockInVendor = null;

        stockInEmployee = employeeRepository.findById(theStockInDTO.getEmployeeId())
                .orElseThrow( ()-> new RuntimeException("Employee " + theStockInDTO.getEmployeeId() + " is not exist"));

        stockInItem = itemRepository.findByItemName(theStockInDTO.getStockInItemName());
//                .orElseThrow( ()-> new RuntimeException("Item " + theStockInDTO.getStockInItemName() + " is not exist"));

        stockInVendor = vendorRepository.findByVendorName(theStockInDTO.getStockInVendorName());
//                .orElseThrow( ()-> new RuntimeException("Vendor " + theStockInDTO.getStockInVendorName() + " is not exist"));

        theStockIn.setStockInEmployee(stockInEmployee);
        theStockIn.setStockInItem(stockInItem);
        theStockIn.setStockInPrice(stockInPrice);
        theStockIn.setStockInQuantity(stockInQuantity);
        theStockIn.setStockInVendor(stockInVendor);
        theStockIn.setStockInTotalPrice(stockInTotalPrice);

        Stock theStock = stockRepository.findByItemId(stockInItem.getItemId());

        if(theStockIn.getStockInId() == 0){
            theStock.setItem(stockInItem);
            stockRepository.save(theStock.addQuantity(stockInQuantity));
        }else{
            stockRepository.save(theStock.addQuantity(stockInQuantity - stockInRepository.findById(theStockIn.getStockInId()).get().getStockInQuantity()));
        }

        stockInRepository.save(theStockIn);
//        System.out.println(theStockIn);

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

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ErrorResponse> NonUniqueResultException(IncorrectResultSizeDataAccessException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }






}


