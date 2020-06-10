package flower.rest.server;

import flower.rest.server.entity.Anniversary;
import flower.rest.server.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

public class ControllerTools {

    public static <V> String deleteById(JpaRepository<V, Integer> repo, int valueId){
        Optional<V> resultOpt = repo.findById(valueId);

        if(resultOpt.isPresent()){
            V result = resultOpt.get();
            repo.deleteById(valueId);
            return "Deleted " + result.getClass().getSimpleName() + " : " + result;
        }else{
            throw new RuntimeException("Id " + valueId + " is not exist");
        }
    }

    public static <V> V saveValue(JpaRepository<V, Integer> repo, V value){
        repo.save(value);
        return value;
    }

    public static <V> V findById(JpaRepository<V, Integer> repo, int valueId){
        Optional<V> resultOpt = repo.findById(valueId);

        if(resultOpt.isPresent()){
            V result = resultOpt.get();
            return result;
        }else{
            throw new RuntimeException("Id " + valueId + " is not exist");
        }
    }

    public static <V> List<V> findAll(JpaRepository<V, Integer> repo){
        return repo.findAll();
    }

    public static String decodeSearchContent(String content){
        String decodedContent = null;
        try {
            decodedContent = URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return decodedContent;
        }
    }
}
