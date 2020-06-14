package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.MembershipRepository;
import flower.rest.server.dao.TransactionRepository;
import flower.rest.server.dto.TransactionDTO;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Membership;
import flower.rest.server.entity.StockOut;
import flower.rest.server.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transactions1")
public class TransactionController extends StockOutsBaseController{

    private TransactionRepository transactionRepository;
    private MembershipRepository membershipRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, MembershipRepository membershipRepository) {
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

    private Transaction transactionDTO2Loss(TransactionDTO theTransactionDTO){
        List<StockOut> theStockOuts = super.createStockOutsList(theTransactionDTO);
        Employee theEmployee = super.getEmployee(theTransactionDTO);
        Membership theMembership = this.membershipRepository.findById(theTransactionDTO.getMembershipId())
                .orElseThrow(()->new RuntimeException("The membership " + theTransactionDTO.getMembershipId() + " is not exist"));
        double finalAmount = theStockOuts.stream()
                .collect(Collectors.summingDouble(s->s.getStockOutQuantity()*s.getPriceFinal()));

        Transaction resultTransaction = new Transaction();
        resultTransaction.setTransactionEmployee(theEmployee);
        resultTransaction.setTransactionStockOuts(theStockOuts);
        resultTransaction.setTransactionMembership(theMembership);
        resultTransaction.setTransactionAmount(finalAmount);

        return resultTransaction;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO theTransactionDTO){

        Transaction theTransaction = this.transactionDTO2Loss(theTransactionDTO);

        transactionRepository.save(theTransaction);

        return theTransaction;
    }

    @PutMapping("/{transactionId}")
    public Transaction updateTransaction(@RequestBody TransactionDTO theTransactionDTO, @PathVariable int transactionId){

        Transaction originTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new RuntimeException("The transaction is not exist "));

        //record the originStockOutIds
        List<Integer> originStockOutIds = originTransaction.getTransactionStockOuts().stream()
                .map(s->s.getStockOutId())
                .collect(Collectors.toList());

        //rollBackStock
        super.rollBackStock(originTransaction.getTransactionStockOuts());

        //create new transaction and set stock outs and stock changes
        Transaction theTransaction = this.transactionDTO2Loss(theTransactionDTO);
        theTransaction.setTransactionId(transactionId);

        //save the changes
        if (theTransaction != null) {
            transactionRepository.save(theTransaction);
        }else{
            throw new RuntimeException("The Transaction cannot be null! ");
        }
        //delete the useless stock outs
        originStockOutIds.removeAll(theTransactionDTO.getStockOutIds());
        originStockOutIds.stream().forEach(id->super.stockOutRepository.deleteById(id));


        return theTransaction;
    }

    @DeleteMapping("/{transactionId}")
    public String deleteTransactionById(@PathVariable int transactionId){

        Transaction theTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new RuntimeException("The transaction is not exist "));
        super.rollBackStock(theTransaction.getTransactionStockOuts());
        transactionRepository.deleteById(transactionId);
        return "Deleted transaction " + transactionId;
    }

}
