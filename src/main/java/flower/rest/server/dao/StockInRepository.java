package flower.rest.server.dao;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin("*")
public interface StockInRepository extends JpaRepository<StockIn, Integer> {

//    public List<StockIn> findByStock_in_vendor(@RequestParam Integer id);
}
