package flower.rest.server.dao;

import flower.rest.server.entity.Item;
import flower.rest.server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
