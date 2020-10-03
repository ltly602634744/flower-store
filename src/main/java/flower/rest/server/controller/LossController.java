package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.ErrorResponse.ErrorResponse;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.LossRepository;
import flower.rest.server.dto.LossDTO;
import flower.rest.server.dto.change.LossDisplay;
import flower.rest.server.dto.change.StockOutWithItemName;
import flower.rest.server.entity.Loss;
import flower.rest.server.entity.StockOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/losses1")
public class LossController extends StockOutsBaseController{

    private LossRepository lossRepository;
//    private EmployeeRepository employeeRepository;
//    private int employeeId;
//    private LossDTO theLossDTO;

    @Autowired
    public LossController(LossRepository lossRepository) {
        this.lossRepository = lossRepository;
//        this.employeeRepository = employeeRepository;
    }


//    @GetMapping
//    public List<Loss> findAllLosses(){
//        return ControllerTools.findAll(lossRepository);
//    }

    @GetMapping("/{lossId}")
    public Loss findLossById(@PathVariable int lossId) throws Exception {
        return ControllerTools.findById(lossRepository, lossId);
    }

    //    @GetMapping("/{lossId}/stockOuts")
//    public List<StockOut> findStockOutsByLossById(@PathVariable int lossId) throws Exception {
//        return ControllerTools.findById(lossRepository, lossId).getLossStockOuts();
//    }




    @GetMapping
    public List<LossDisplay> findAllLosses(){
        return ControllerTools.findAll(lossRepository).stream()
                .map(x->{
                    LossDisplay lossDisplay = new LossDisplay();
                    return lossDisplay.loss2LossDisplay(x);
                }).collect(Collectors.toList());
    }
//    @GetMapping("/{lossId}")
//    public LossDisplay findLossById(@PathVariable int lossId) throws Exception {
//        LossDisplay lossDisplay = new LossDisplay();
//        return lossDisplay.loss2LossDisplay(ControllerTools.findById(lossRepository, lossId));
//    }

    @GetMapping("/{lossId}/stockOuts")
    public List<StockOutWithItemName> findStockOutsByLossById(@PathVariable int lossId) throws Exception {

        return ControllerTools.findById(lossRepository, lossId).getLossStockOuts().stream()
                .map(x->{
                    StockOutWithItemName stockOutWithItemName = new StockOutWithItemName();
                    return stockOutWithItemName.StockOut2StockOutWithItemName(x);
                }).collect(Collectors.toList());
    }

//    private Loss lossDTO2Loss(LossDTO theLossDTO){
//
//        List<StockOut> stockOuts = super.createStockOutsList(theLossDTO);
//
//        Loss resultLoss = new Loss();
//        resultLoss.setLossEmployee(super.getEmployee(theLossDTO));
//        resultLoss.setLossStockOuts(stockOuts);
//
//        return resultLoss;
//    }

    @PostMapping
    public Loss createLoss(@RequestBody LossDTO theLossDTO){
//        Loss theLoss  = this.lossDTO2Loss(theLossDTO);
        Loss theLoss  = new Loss();

//        this.employeeId = theLossDTO.getEmployeeId();
        theLoss.setLossEmployee(super.employeeRepository.findById(theLossDTO.getEmployeeId()).get());
        theLoss.setLossStockOuts(new ArrayList<>());
        this.lossRepository.save(theLoss);
//        this.theLossDTO = theLossDTO;
        return theLoss;

//        return theLoss;
    }

    @PostMapping("/{lossId}/detail")
    public Loss createLossDetail(@PathVariable int lossId, @RequestBody LossDTO theLossDTO){

//        theLossDTO.setEmployeeId(this.employeeId);
        Loss theLoss  = this.lossRepository.findById(lossId)
                .orElseThrow(() -> new RuntimeException("Cannot find the loss " + lossId));
//        int quantity = theLossDTO.getItemQuantity();
//        int itemId = theLossDTO.getItemId();
        theLoss.getLossStockOuts().add(super.createStockOut(theLossDTO));


//        this.theLoss.getLossStockOuts().add(new StockOut(0，));

        lossRepository.save(theLoss);

        return theLoss;
    }


//    @PostMapping
//    public Loss createLoss(@RequestBody LossDTO theLossDTO){
//        Loss theLoss  = this.lossDTO2Loss(theLossDTO);
//
//        lossRepository.save(theLoss);
//
//        return theLoss;
//    }

    @PutMapping("/{lossId}")
    public Loss updateLossEmployee(@RequestBody LossDTO theLossDTO, @PathVariable int lossId){
        Loss theLoss = lossRepository.findById(lossId)
                .orElseThrow(()-> new RuntimeException("The loss is not exist "));
        theLoss.setLossEmployee(super.employeeRepository.findById(theLossDTO.getEmployeeId())
                .orElseThrow(()-> new RuntimeException("The employee is not exist ")));
        this.lossRepository.save(theLoss);
        return theLoss;
    }

    @PutMapping("/{lossId}/detail")
    public Loss updateLossDetail(@RequestBody LossDTO theLossDTO, @PathVariable int lossId){
        Loss theLoss = lossRepository.findById(lossId)
                .orElseThrow(()-> new RuntimeException("The loss is not exist "));

        StockOut theStockOut = super.stockOutRepository.findById(theLossDTO.getStockOutId())
                .orElseThrow(()-> new RuntimeException("The StockOut is not exist "));

        super.rollBackOneStock(theStockOut);

//        theLoss.getLossStockOuts().add(super.createStockOut(theLossDTO));

        StockOut theNewStockOut = super.createStockOut(theLossDTO);

        super.stockOutRepository.save(theNewStockOut);

        lossRepository.save(theLoss);

        return theLoss;

    }



//    @PutMapping("/{lossId}")
//    public Loss updateLoss(@RequestBody LossDTO theLossDTO, @PathVariable int lossId){
//
//        Loss originLoss = lossRepository.findById(lossId)
//                .orElseThrow(()-> new RuntimeException("The loss is not exist "));
//
//        //record the originStockOutIds
//        List<Integer> originStockOutIds = originLoss.getLossStockOuts().stream()
//                .map(s -> s.getStockOutId())
//                .collect(Collectors.toList());
//
//        //rollBackStock
//        super.rollBackStock(originLoss.getLossStockOuts());
//
//        Loss theLoss = lossDTO2Loss(theLossDTO);
//        theLoss.setLossId(lossId);
//
//        //save the changes
//        if (theLoss != null) {
//            lossRepository.save(theLoss);
//        }else{
//            throw new RuntimeException("The loss cannot be null! ");
//        }
//        //delete the useless stock outs
//        originStockOutIds.removeAll(theLossDTO.getStockOutIds());
//        originStockOutIds.stream().forEach(id->super.stockOutRepository.deleteById(id));
//
//
//        return theLoss;
//    }

    @DeleteMapping("/{lossId}")
    public String deleteLossById(@PathVariable int lossId){
        Loss theLoss = lossRepository.findById(lossId)
                .orElseThrow(()-> new RuntimeException("The loss is not exist "));
        super.rollBackStock(theLoss.getLossStockOuts());
        lossRepository.deleteById(lossId);
        return "Deleted loss " + lossId;
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


