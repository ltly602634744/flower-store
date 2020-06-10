package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.*;
import flower.rest.server.dto.LossDTO;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Loss;
import flower.rest.server.entity.Stock;
import flower.rest.server.entity.StockOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/losses1")
public class LossController extends StockOutsBaseController{

    private LossRepository lossRepository;


    @Autowired
    public LossController(LossRepository lossRepository) {
        this.lossRepository = lossRepository;
//        this.stockOutRepository = stockOutRepository;
//        this.stockRepository = stockRepository;
//        this.employeeRepository = employeeRepository;
//        this.itemRepository = itemRepository;
    }


    @GetMapping
    public List<Loss> findAllLosses(){
        return ControllerTools.findAll(lossRepository);
    }

    @GetMapping("/{lossId}")
    public Loss findLossById(@RequestParam int lossId) throws Exception {
        return ControllerTools.findById(lossRepository, lossId);
    }

    private Loss lossDTO2Loss(LossDTO theLossDTO){

        Loss resultLoss = null;
        Employee theEmployee = null;
        theEmployee = employeeRepository.findById(theLossDTO.getEmployeeId())
                .orElseThrow(()->new RuntimeException("Cannot find the employee " + theLossDTO.getEmployeeId()));

        List<Integer> itemIds = theLossDTO.getLossItemIds();
        List<Integer> itemQuantities = theLossDTO.getLossItemQuantities();

        // test whether the size of lists is same
        if(itemIds.size() != itemQuantities.size()){
            throw new RuntimeException("The sizes of two lists are not equal!");
        }

        //create entry stream
        Map<Integer, Integer> idQuantityMap = itemIds.stream()
                .collect(Collectors.toMap(id -> id, id -> itemQuantities.get(itemIds.indexOf(id))));

        // test whether the quantity is over limit
        for (Map.Entry<Integer, Integer> tempEntry : idQuantityMap.entrySet()){
            int id = tempEntry.getKey();
            int quantity = tempEntry.getValue();
            itemRepository.findById(id).orElseThrow(()->new RuntimeException("Item id " + id + " is not exist!"));
            if (stockRepository.findByItemId(id).getStockQuantity() < quantity){
                throw new RuntimeException( "stock quantity of item " + id + " is not enough!");
            }
        }

        //create StockOut record and change the stock quantities
        List<StockOut> stockOuts = idQuantityMap.entrySet().stream()
                                    .map((entry) -> {
                                        int id = entry.getKey();
                                        int quantity = entry.getValue();

                                        StockOut tempStockOut = new StockOut();
                                        tempStockOut.setPriceFinal(0.0);
                                        tempStockOut.setStockOutQuantity(quantity);
                                        tempStockOut.setStockOutItem(itemRepository.findById(id).get());

                                        Stock tempStock = stockRepository.findByItemId(id);
                                        tempStock.addQuantity(-quantity);

                                        stockRepository.save(tempStock);
                                        stockOutRepository.save(tempStockOut);
                                        return tempStockOut;
                                    })
                                    .collect(Collectors.toList());

        resultLoss = new Loss();
        resultLoss.setLossEmployee(theEmployee);
        resultLoss.setLossStockOuts(stockOuts);

        return resultLoss;
    }

    @PostMapping
    public Loss createLoss(@RequestBody LossDTO theLossDTO){
        Loss theLoss = null;
        theLoss = lossDTO2Loss(theLossDTO);

        if (theLoss != null) {
            lossRepository.save(theLoss);
        }else{
            throw new RuntimeException("The loss cannot be null! ");
        }

        return theLoss;
    }

//    @PutMapping("/{lossId}")
//    public Loss updateLoss(@RequestBody LossDTO theLossDTO, @PathVariable int lossId){
//
//        Loss originLoss = lossRepository.findById(lossId)
//                .orElseThrow(()-> new RuntimeException("The loss is not exist "));
//
//        theLossDTO.set
//        Loss theLoss = null;
////        theLoss = lossDTO2Loss(theLossDTO);
//
//        if (theLoss != null) {
//            lossRepository.save(theLoss);
//        }else{
//            throw new RuntimeException("The loss cannot be null! ");
//        }
//
//        return theLoss;
//    }



}


