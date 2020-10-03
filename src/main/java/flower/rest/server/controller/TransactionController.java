package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.MembershipRepository;
import flower.rest.server.dao.PriceRepository;
import flower.rest.server.dao.TransactionRepository;
import flower.rest.server.dto.LossDTO;
import flower.rest.server.dto.StockOutDTO;
import flower.rest.server.dto.TransactionDTO;
import flower.rest.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transactions1")
public class TransactionController extends StockOutsBaseController{

    private TransactionRepository transactionRepository;
    private MembershipRepository membershipRepository;
    private PriceRepository priceRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, MembershipRepository membershipRepository,
                                 PriceRepository priceRepository) {
        this.transactionRepository = transactionRepository;
        this.membershipRepository = membershipRepository;
    }

    @GetMapping
    public List<Transaction> findAllTransactions(){
        return ControllerTools.findAll(transactionRepository);
    }

    @GetMapping("/transactionId")
    public Transaction findTransactionById(@PathVariable int transactionId){
        return ControllerTools.findById(transactionRepository, transactionId);
    }

//    private Transaction transactionDTO2Loss(TransactionDTO theTransactionDTO){
//        StockOut theStockOuts = super.createStockOut(theTransactionDTO);
//        Employee theEmployee = theTransactionDTO.get;
//        Membership theMembership = this.membershipRepository.findById(theTransactionDTO.getMembershipId())
//                .orElseThrow(()->new RuntimeException("The membership " + theTransactionDTO.getMembershipId() + " is not exist"));
//        double finalAmount = theStockOuts.stream()
//                .collect(Collectors.summingDouble(s->s.getStockOutQuantity()*s.getPriceFinal()));
//
//        Transaction resultTransaction = new Transaction();
//        resultTransaction.setTransactionEmployee(theEmployee);
//        resultTransaction.setTransactionStockOuts(theStockOuts);
//        resultTransaction.setTransactionMembership(theMembership);
//        resultTransaction.setTransactionAmount(finalAmount);
//
//        return resultTransaction;
//    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO theTransactionDTO){
        Transaction theTransaction  = new Transaction();

        theTransaction.setTransactionEmployee(super.employeeRepository.findById(theTransactionDTO.getEmployeeId()).get());
        theTransaction.setTransactionStockOuts(new ArrayList<>());
        theTransaction.setTransactionMembership(membershipRepository.findById(theTransactionDTO.getMembershipId())
                              .orElseThrow(()-> new RuntimeException("The membership is not exist ")));
        this.transactionRepository.save(theTransaction);
//        this.theLossDTO = theLossDTO;
        return theTransaction;
    }

    //TO DO:
    @PostMapping("/{transactionId}/detail")
    public Transaction createTransactionDetail(@PathVariable int transactionId,
                                        @RequestBody StockOutDTO theStockOutDTO){

//        theLossDTO.setEmployeeId(this.employeeId);
        Transaction theTransaction  = this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Cannot find the transaction " + transactionId));
//        int quantity = theLossDTO.getItemQuantity();
//        int itemId = theLossDTO.getItemId();
        theTransaction.getTransactionStockOuts().add(super.createStockOut(theStockOutDTO));

        double transactionPrice = theTransaction.getTransactionAmount();
        Item item = super.itemRepository.findByItemName(theStockOutDTO.getItemName());
        List<Price> prices = this.priceRepository.findByPriceItemId(item.getItemId());
        theTransaction.setTransactionAmount(transactionPrice + prices.get(prices.size()-1).getPrice() * theStockOutDTO.getItemQuantity());

//        this.theLoss.getLossStockOuts().add(new StockOut(0，));

        transactionRepository.save(theTransaction);

        return theTransaction;
    }

//    @PutMapping("/{transactionId}")
//    public Transaction updateTransaction(@RequestBody TransactionDTO theTransactionDTO, @PathVariable int transactionId){
//
//        Transaction originTransaction = transactionRepository.findById(transactionId)
//                .orElseThrow(()-> new RuntimeException("The transaction is not exist "));
//
//        //record the originStockOutIds
//        List<Integer> originStockOutIds = originTransaction.getTransactionStockOuts().stream()
//                .map(s->s.getStockOutId())
//                .collect(Collectors.toList());
//
//        //rollBackStock
//        super.rollBackStock(originTransaction.getTransactionStockOuts());
//
//        //create new transaction and set stock outs and stock changes
//        Transaction theTransaction = this.transactionDTO2Loss(theTransactionDTO);
//        theTransaction.setTransactionId(transactionId);
//
//        //save the changes
//        if (theTransaction != null) {
//            transactionRepository.save(theTransaction);
//        }else{
//            throw new RuntimeException("The Transaction cannot be null! ");
//        }
//        //delete the useless stock outs
//        originStockOutIds.removeAll(theTransactionDTO.getStockOutIds());
//        originStockOutIds.stream().forEach(id->super.stockOutRepository.deleteById(id));
//
//
//        return theTransaction;
//    }

    @DeleteMapping("/{transactionId}")
    public String deleteTransactionById(@PathVariable int transactionId){

        Transaction theTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new RuntimeException("The transaction is not exist "));
        super.rollBackStock(theTransaction.getTransactionStockOuts());
        transactionRepository.deleteById(transactionId);
        return "Deleted transaction " + transactionId;
    }

}
