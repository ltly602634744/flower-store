package flower.rest.server.controller;

import flower.rest.server.ControllerTools;
import flower.rest.server.dao.ItemRepository;
import flower.rest.server.dao.VendorRepository;
import flower.rest.server.entity.Item;
import flower.rest.server.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items1")
public class ItemController {

    private ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    public List<Item> findAll(){
        return ControllerTools.findAll(itemRepository);
    }

    @GetMapping("/{itemId}")
    public Item findItemById(@PathVariable int itemId){
        return ControllerTools.findById(itemRepository, itemId);
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
}
