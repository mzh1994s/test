package cn.mzhong.test.lambda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionTest {

    @Test
    public void testFilter() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5);
        List<Integer> collect = list.stream().filter(item -> item > 2).collect(Collectors.toList());
        System.out.println(collect);
    }

}
