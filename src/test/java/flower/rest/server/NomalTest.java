package flower.rest.server;

import flower.rest.server.entity.StockOut;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class NomalTest {
    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Integer integer = 2;
        list.add(integer);

        System.out.println(list.contains(list.get(1)-list.get(0)));
    }


//    private <V> void test2(V v){
//
//        Foo<V> foo = new Foo<>(){};
//        // 在类的外部这样获取
//        Type type = ((ParameterizedType)foo.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        System.out.println(type.getTypeName());
//    }
//
//    @Test
//    public void test3(){
//        test2(new String());
//    }
}

abstract class Foo<T>{
    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
