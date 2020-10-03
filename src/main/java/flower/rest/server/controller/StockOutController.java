package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.LossRepository;
import flower.rest.server.dao.StockOutRepository;
import flower.rest.server.dao.StockRepository;
import flower.rest.server.dao.TransactionRepository;
import flower.rest.server.dto.StockOutDisplay;
import flower.rest.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/stockOuts1")
public class StockOutController {

    private StockOutRepository stockOutRepository;
    private LossRepository lossRepository;
    private TransactionRepository transactionRepository;
    private StockRepository stockRepository;
//    private List<StockOutDisplay> losses;
//    private List<StockOutDisplay> transactions;


    @Autowired
    public StockOutController(StockOutRepository stockOutRepository, LossRepository lossRepository,
                              TransactionRepository transactionRepository, StockRepository stockRepository) {
        this.stockOutRepository = stockOutRepository;
        this.lossRepository = lossRepository;
        this.transactionRepository = transactionRepository;
        this.stockOutRepository = stockOutRepository;
//        this.lossStream = this.transferFromLoss()
    }

//    @Autowired
//    public StockOutController(StockOutRepository stockOutRepository, LossRepository lossRepository,
//                              TransactionRepository transactionRepository,
//                              @Qualifier("lossList") List<StockOutDisplay> losses,
//                              @Qualifier("transactionList") List<StockOutDisplay> transactions) {
//        this.stockOutRepository = stockOutRepository;
//        this.lossRepository = lossRepository;
//        this.transactionRepository = transactionRepository;
//        this.losses = losses;
//        this.transactions = transactions;
//    }



    private Stream<StockOutDisplay> transferFromLoss(Loss theLoss){
        return
                theLoss.getLossStockOuts().stream()
                        .map((s)->new StockOutDisplay(s.getStockOutId(), theLoss.getLossTime(), theLoss.getLossEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"折损"));
    }

    private Stream<StockOutDisplay> transferFromTransaction(Transaction theTransaction){
        return
                theTransaction.getTransactionStockOuts().stream()
                        .map((s)->new StockOutDisplay(s.getStockOutId(), theTransaction.getTransactionTime(), theTransaction.getTransactionEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"交易"));

    }

    private Stream<StockOutDisplay> getAllStockOutsStream(){
        return
        Stream.concat(lossRepository.findAll().stream().flatMap(loss->transferFromLoss(loss)),
                transactionRepository.findAll().stream().flatMap(transaction->transferFromTransaction(transaction)));
    }

    @GetMapping
    public List<StockOutDisplay> findAllStockOuts(){

        return this.getAllStockOutsStream()
                .sorted((x, y)-> -(x.getStockOutTime().compareTo(y.getStockOutTime())))
                .collect(Collectors.toList());

    }


    @GetMapping("/fuzzySearch")
    public List<StockOutDisplay> fuzzySearch(@RequestParam("content") String content){
        String decodedContent = ControllerTools.decodeSearchContent(content);
        return
                this.getAllStockOutsStream()
                    .filter(x->x.getStockOutItem().getItemName().contains(content)
                            ||x.getStockOutCategory().contains(content))
                        .sorted((x, y)-> -(x.getStockOutTime().compareTo(y.getStockOutTime())))
                        .collect(Collectors.toList());

    }

    @DeleteMapping("/{stockOutId}")
    public String deleteStockOutById(@PathVariable int stockOutId){
        StockOut theStockOut = this.stockOutRepository.findById(stockOutId)
                .orElseThrow(()-> new RuntimeException("The stockOut is not exist "));

        Stock tempStock = stockRepository.findByItemId(theStockOut.getStockOutItem().getItemId());
        tempStock.addQuantity(theStockOut.getStockOutQuantity());
        stockRepository.save(tempStock);

        this.stockOutRepository.deleteById(stockOutId);

        return "Deleted stockOut " + stockOutId;

    }



}

//@Configuration
//class StockOutsBean{
//
//    private LossRepository lossRepository;
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    public StockOutsBean(LossRepository lossRepository, TransactionRepository transactionRepository) {
//        this.lossRepository = lossRepository;
//        this.transactionRepository = transactionRepository;
//    }
//
//    private Stream<StockOutDisplay> transferFromLoss(Loss theLoss){
//        return
//                theLoss.getLossStockOuts().stream()
//                        .map((s)->new StockOutDisplay(s.getStockOutId(), theLoss.getLossTime(), theLoss.getLossEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"折损"));
//    }
//
//    private Stream<StockOutDisplay> transferFromTransaction(Transaction theTransaction){
//        return
//                theTransaction.getTransactionStockOuts().stream()
//                        .map((s)->new StockOutDisplay(s.getStockOutId(), theTransaction.getTransactionTime(), theTransaction.getTransactionEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"交易"));
//
//    }
//
//    @Bean(name = "lossList")
//    public List<StockOutDisplay> lossList(){
//        return lossRepository.findAll().stream().flatMap((loss)->transferFromLoss(loss)).collect(Collectors.toList());
//    }
//    @Bean(name = "transactionList")
//    public List<StockOutDisplay> transactionList(){
//        return transactionRepository.findAll().stream().flatMap((transaction)->transferFromTransaction(transaction)).collect(Collectors.toList());
//    }
//}




