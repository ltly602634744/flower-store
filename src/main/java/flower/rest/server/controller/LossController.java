package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.LossRepository;
import flower.rest.server.dto.LossDTO;
import flower.rest.server.entity.Loss;
import flower.rest.server.entity.StockOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/losses1")
public class LossController extends StockOutsBaseController{

    private LossRepository lossRepository;


    @Autowired
    public LossController(LossRepository lossRepository) {
        this.lossRepository = lossRepository;
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

        List<StockOut> stockOuts = super.createStockOutsList(theLossDTO);

        Loss resultLoss = new Loss();
        resultLoss.setLossEmployee(super.getEmployee(theLossDTO));
        resultLoss.setLossStockOuts(stockOuts);

        return resultLoss;
    }

    @PostMapping
    public Loss createLoss(@RequestBody LossDTO theLossDTO){
        Loss theLoss  = this.lossDTO2Loss(theLossDTO);

        lossRepository.save(theLoss);

        return theLoss;
    }

    @PutMapping("/{lossId}")
    public Loss updateLoss(@RequestBody LossDTO theLossDTO, @PathVariable int lossId){

        Loss originLoss = lossRepository.findById(lossId)
                .orElseThrow(()-> new RuntimeException("The loss is not exist "));

        //record the originStockOutIds
        List<Integer> originStockOutIds = originLoss.getLossStockOuts().stream()
                .map(s -> s.getStockOutId())
                .collect(Collectors.toList());

        //rollBackStock
        super.rollBackStock(originLoss.getLossStockOuts());

        Loss theLoss = lossDTO2Loss(theLossDTO);
        theLoss.setLossId(lossId);

        //save the changes
        if (theLoss != null) {
            lossRepository.save(theLoss);
        }else{
            throw new RuntimeException("The loss cannot be null! ");
        }
        //delete the useless stock outs
        originStockOutIds.removeAll(theLossDTO.getStockOutIds());
        originStockOutIds.stream().forEach(id->super.stockOutRepository.deleteById(id));


        return theLoss;
    }

    @DeleteMapping("/{lossId}")
    public String deleteLossById(@PathVariable int lossId){
        Loss theLoss = lossRepository.findById(lossId)
                .orElseThrow(()-> new RuntimeException("The loss is not exist "));
        super.rollBackStock(theLoss.getLossStockOuts());
        lossRepository.deleteById(lossId);
        return "Deleted loss " + lossId;
    }



}


