package flower.rest.server.dao;

import flower.rest.server.entity.Item;
import flower.rest.server.entity.Vendor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findByItemName(String name);
    @RestResource(rel="fuzzySearch", path = "fuzzySearch")
    List<Item> findByItemNameContainingOrItemLocationContaining(@RequestParam String name, @RequestParam String location);
}
