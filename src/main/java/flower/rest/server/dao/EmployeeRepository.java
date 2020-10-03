package flower.rest.server.dao;

import flower.rest.server.entity.Employee;
import flower.rest.server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByEmployeeStatus(String status);

    @RestResource(rel="fuzzySearch", path = "fuzzySearch")
    List<Employee> findByEmployeeNameContainingOrEmployeeTelephoneContainingOrEmployeeStatusContaining(String name, String telephone, String status);
//    @RestResource(rel="fuzzySearch", path = "fuzzySearch")
//    List<Employee> findByEmployeeNameContainingOrEmployeeTelephoneContaining(String name, String telephone);
}
