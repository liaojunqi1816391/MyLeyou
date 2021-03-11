package com.leyou.item.test;

import com.leyou.LeyouItemServiceApplication;
import com.leyou.item.dao.SkuMapper;
import com.leyou.item.pojo.Sku;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LeyouItemServiceApplication.class)
public class MapperTest {

    @Autowired
    private SkuMapper skuMapper;

    @Test
    public void testSkuMapper(){
        List<Long> list = new ArrayList<>();
        list.add(2868393L);
        list.add(2868435L);

//        Sku sku = skuMapper.selectByPrimaryKey(2868435L);
//        System.out.println(sku);

        List<Sku> skus = skuMapper.queryByIds(list);
        System.out.println(skus);
    }
}
