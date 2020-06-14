package flower.rest.server.controller;

import flower.rest.server.dao.LossRepository;
import flower.rest.server.dao.StockOutRepository;
import flower.rest.server.dao.TransactionRepository;
import flower.rest.server.dto.StockOutDisplay;
import flower.rest.server.entity.Loss;
import flower.rest.server.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public StockOutController(StockOutRepository stockOutRepository, LossRepository lossRepository, TransactionRepository transactionRepository) {
        this.stockOutRepository = stockOutRepository;
        this.lossRepository = lossRepository;
        this.transactionRepository = transactionRepository;
    }

    private Stream<StockOutDisplay> transferFromLoss(Loss theLoss){
        return
                theLoss.getLossStockOuts().stream()
                        .map((s)->new StockOutDisplay(theLoss.getLossTime(), theLoss.getLossEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"loss"));
    }

    private Stream<StockOutDisplay> transferFromTransaction(Transaction theTransaction){
        return
                theTransaction.getTransactionStockOuts().stream()
                        .map((s)->new StockOutDisplay(theTransaction.getTransactionTime(), theTransaction.getTransactionEmployee(), s.getStockOutItem(), s.getStockOutQuantity(), s.getPriceFinal(),"transaction"));

    }

    @GetMapping
    public List<StockOutDisplay> findAllStockOuts(){

        return
        Stream.concat(lossRepository.findAll().stream().flatMap((loss)->transferFromLoss(loss)),
                transactionRepository.findAll().stream().flatMap((transaction)->transferFromTransaction(transaction)))
                .sorted((x, y)-> -(x.getStockOutTime().compareTo(y.getStockOutTime())))
                .collect(Collectors.toList());

    }
}




