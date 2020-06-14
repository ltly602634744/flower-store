package flower.rest.server.controller;

import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.StockOutRepository;
import flower.rest.server.dao.StockRepository;
import flower.rest.server.dto.StockOutsDTO;
import flower.rest.server.entity.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class StockOutsBaseController {

    protected StockOutRepository stockOutRepository;
    protected StockRepository stockRepository;
    protected EmployeeRepository employeeRepository;
    protected ItemRepository itemRepository;

    @Autowired
    public void setStockOutRepository(StockOutRepository stockOutRepository) {
        this.stockOutRepository = stockOutRepository;
    }

    @Autowired
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    protected Employee getEmployee(StockOutsDTO theStockOutsDTO){
        Employee theEmployee = employeeRepository.findById(theStockOutsDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Cannot find the employee " + theStockOutsDTO.getEmployeeId()));
        return theEmployee;
    }

    private boolean sameSizeLists(List ... lists){
        int size = lists[0].size();
        for(int i = 1; i < lists.length; i++){
            if (lists[i].size() != size){
                return false;
            }
        }
        return true;
    }



    protected List<StockOut> createStockOutsList(StockOutsDTO theStockOutsDTO){

        List<Integer> stockOutIds = theStockOutsDTO.getStockOutIds();
        List<Integer> itemIds = theStockOutsDTO.getItemIds();
        List<Integer> itemQuantities = theStockOutsDTO.getItemQuantities();
        List<Double> finalUnitPrices = theStockOutsDTO.getFinalUnitPrices();

        // test whether the size of lists is same
        if(!this.sameSizeLists(stockOutIds, itemIds, itemQuantities, finalUnitPrices)){
            throw new RuntimeException("The sizes of the lists are not equal!");
        }

        // test whether the quantity is over limit, the item is exist
        for(int i = 0; i < itemIds.size(); i++){
            int id = itemIds.get(i);
            int quantity = itemQuantities.get(i);
            itemRepository.findById(id).orElseThrow(()->new RuntimeException("Item id " + id + " is not exist!"));
            if (stockRepository.findByItemId(id).getStockQuantity() < quantity){
                throw new RuntimeException( "stock quantity of item " + id + " is not enough!");
            }
        }

        //create StockOut record and change the stock quantities
        List<StockOut> stockOuts = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++){

            int stockOutId = stockOutIds.get(i);
            int itemId = itemIds.get(i);
            Item item = itemRepository.findById(itemId).get();
            int quantity = itemQuantities.get(i);
            double finalPrice = finalUnitPrices.get(i);

            //create stock out
            StockOut tempStockOut = new StockOut();
            tempStockOut.setStockOutId(stockOutId);
            tempStockOut.setPriceFinal(finalPrice);
            tempStockOut.setStockOutQuantity(quantity);
            tempStockOut.setStockOutItem(item);

            //create stock
            Stock tempStock = stockRepository.findByItemId(itemId);
            tempStock.addQuantity(-quantity);

            //save stock and stockOut
            stockRepository.save(tempStock);
            stockOutRepository.save(tempStockOut);

            //save the stock out into the list
            stockOuts.add(tempStockOut);
        }

//        List<StockOut> stockOuts = idQuantityMap.entrySet().stream()
//                .map((entry) -> {
//                    int id = entry.getKey();
//                    int quantity = entry.getValue();
//
//                    StockOut tempStockOut = new StockOut();
//                    tempStockOut.setPriceFinal(0.0);
//                    tempStockOut.setStockOutQuantity(quantity);
//                    tempStockOut.setStockOutItem(itemRepository.findById(id).get());
//
//                    Stock tempStock = stockRepository.findByItemId(id);
//                    tempStock.addQuantity(-quantity);
//
//                    stockRepository.save(tempStock);
//                    stockOutRepository.save(tempStockOut);
//                    return tempStockOut;
//                })
//                .collect(Collectors.toList());

        return stockOuts;
    }

//    protected StockOuts StockOutsDTO2StockOuts(StockOutsDTO theStockOutsDTO, StockOuts theResult){
//        List<StockOut> stockOuts = this.createStockOutsList(theStockOutsDTO);
//
////        StoukOuts resultTransaction = new Transaction();
//        theResult.set(this.getEmployee(theStockOutsDTO));
//        resultTransaction.setTransactionStockOuts(stockOuts);
//
//        return resultLoss;
//    }

//    protected void updateStockOut(int stockOutId, StockOutDTO stockOutDTO){
//        StockOut theStockOut = new StockOut();
//        theStockOut.setStockOutId(stockOutId);
//        theStockOut.setStockOutItem(stockOutDTO.getStockOutItem());
//
//    }

    protected void deleteStockOut(int stockOutId){
        StockOut theStockOut =  stockOutRepository.findById(stockOutId)
                .orElseThrow(()-> new RuntimeException("The stockOut " + stockOutId + "is not exist"));

        Item theStockOutItem = theStockOut.getStockOutItem();
        Stock theItemStock = stockRepository.findByItemId(theStockOutItem.getItemId());

        theItemStock.addQuantity(theStockOut.getStockOutQuantity());
        stockOutRepository.deleteById(stockOutId);
    }

    protected void rollBackStock(List<StockOut> stockOuts){
        for(StockOut tempStockOut : stockOuts){
            int itemId = tempStockOut.getStockOutItem().getItemId();
            Stock tempStock = stockRepository.findByItemId(itemId);
            tempStock.addQuantity(tempStockOut.getStockOutQuantity());
            stockRepository.save(tempStock);
        }
    }

//    protected void cleanStockOutRepository(){
//        stockOutRepository.findAll().stream().forEach((s)->{
//            if(s.get)
//        });
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
