package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.search.LeyouSearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

//@RunWith(SpringRunner.class)
@SpringBootTest()
public class MyTest {


    @Test
    public void createIndex(){
        // 创建索引库，以及映射
//        this.template.createIndex(Goods.class);
//        this.template.putMapping(Goods.class);
    }

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void test(){

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        queue.add(1);
        queue.add(5);
        queue.add(4);
        queue.add(2);
        queue.add(3);

        System.out.println(queue);
        while (!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }


}
