package flower.rest.server;

import flower.rest.server.dao.VendorRepository;
import flower.rest.server.entity.StockOut;
import flower.rest.server.entity.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class VendorsTest {

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    public void testFuzzySearch (){
//        vendorRepository.
//                findByVendorNameContainingOrVendorContactContainingOrVendorTelephoneContaining("0542","0542","0542")
//                        .stream()
//                        .forEach(System.out::println);
        Stream.of(vendorRepository.findByVendorContactContaining("0542"),
                vendorRepository.findByVendorNameContaining("0542"),
                vendorRepository.findByVendorTelephoneContaining("0542"))
                .flatMap(Collection::stream)
                .distinct()
                .forEach(System.out::println)
                ;
    }

}
