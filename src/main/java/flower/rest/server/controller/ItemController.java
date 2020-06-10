package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.EmployeeRepository;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.PriceRepository;
import flower.rest.server.dao.VendorRepository;
import flower.rest.server.dto.PriceDTO;
import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.Price;
import flower.rest.server.entity.Vendor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items1")
public class ItemController {

    private ItemRepository itemRepository;
    private PriceRepository priceRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, PriceRepository priceRepository,
                            EmployeeRepository employeeRepository){
        this.itemRepository = itemRepository;
        this.priceRepository = priceRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    public List<Item> findAll(){
        return ControllerTools.findAll(itemRepository);
    }

    @GetMapping("/{itemId}")
    public Item findItemById(@PathVariable int itemId){
        return ControllerTools.findById(itemRepository, itemId);
    }

    @GetMapping("/fuzzySearch")
    public List<Item> fuzzySearch(@RequestParam("content") String content) {

        String decodedContent = ControllerTools.decodeSearchContent(content);
        return itemRepository
                .findByItemNameContainingOrItemLocationContaining(decodedContent, decodedContent);
    }

    @PostMapping()
    public Item addItem(@RequestBody Item theItem){

        theItem.setItemId(0);

        this.itemRepository.save(theItem);

        return theItem;
    }

    @PutMapping("/{itemId}")
    public Item updateItem(@PathVariable int itemId,
                             @RequestBody Item theItem){

        theItem.setItemId(itemId);

        this.itemRepository.save(theItem);

        return theItem;
    }

    @DeleteMapping("/{itemId}")
    public String deleteItem(@PathVariable int itemId){
        return ControllerTools.deleteById(itemRepository, itemId);
    }


    @GetMapping("/{itemId}/prices")
    public List<Price> findItemPricesById(@PathVariable int itemId){
        return priceRepository.findByPriceItemId(itemId);
    }

    @GetMapping("/{itemId}/prices/{priceId}")
    public Price findOneItemPriceById(@PathVariable int itemId,  @PathVariable int priceId) throws Exception {
        return priceRepository.findById(priceId)
                .orElseThrow(()-> new Exception(itemId + " not exist"));
    }

    private Price createPriceByPriceDTO(PriceDTO dto, Price thePrice){
        Employee priceEmployee = null;
        try {
            priceEmployee = employeeRepository.findById(dto.getEmployeeId())
                                        .orElseThrow(()-> new Exception(dto.getEmployeeId() + " not exist"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        thePrice.setEmployee(priceEmployee);
        thePrice.setPrice(dto.getPrice());
        thePrice.setDiscount(dto.getDiscount());
        thePrice.setPriceMembership(dto.getPriceMembership());

        priceRepository.save(thePrice);

        return thePrice;
    }

    @PostMapping("/{itemId}/prices")
    public Price createPriceByItemId(@PathVariable int itemId, @RequestBody PriceDTO thePriceDTO){
        Price thePrice = new Price();

        thePrice.setPriceId(0);
        thePrice.setPriceItem(itemRepository.findById(itemId).get());

        return createPriceByPriceDTO(thePriceDTO, thePrice);
    }

    @PutMapping("/{itemId}/prices/{priceId}")
    public Price updatePriceByItemId(@PathVariable int itemId,
                                     @PathVariable int priceId,
                                     @RequestBody PriceDTO thePriceDTO){
        Price thePrice = new Price();

        thePrice.setPriceId(priceId);
        thePrice.setPriceItem(itemRepository.findById(itemId).get());

        return createPriceByPriceDTO(thePriceDTO, thePrice);
    }

    @DeleteMapping("/{itemId}/prices/{priceId}")
    public String deletePriceByItemId(@PathVariable int itemId,
                                      @PathVariable int priceId){
        return ControllerTools.deleteById(priceRepository, priceId);
    }


}


