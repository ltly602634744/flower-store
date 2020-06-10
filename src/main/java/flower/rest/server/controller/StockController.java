package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.StockRepository;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/stocks1")
public class StockController {

    private StockRepository stockRepository;

    @Autowired
    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping()
    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    @GetMapping("/{stockId}")
    public Stock findStockById(@PathVariable int stockId){
        return ControllerTools.findById(stockRepository, stockId);
    }

    @GetMapping("/searchByItemId")
    public Stock findStockByItemId(@RequestParam int itemId){
        return stockRepository.findByItemId(itemId);
    }

    @GetMapping("/searchByItemName")
    public List<Stock> findStockByItemName(@RequestParam String itemName){
        return stockRepository.findByItemName(itemName);
    }

    @GetMapping("/fuzzySearch")
    public List<Stock> fuzzySearch(@RequestParam("content") String content){
        String decodedContent = ControllerTools.decodeSearchContent(content);
        return stockRepository
                .findByItemName(decodedContent);
    }
}
