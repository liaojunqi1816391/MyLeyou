package com.leyou.item.dao;

import com.leyou.item.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuMapper extends Mapper<Sku> {
    @Select({"<script>",
             "select * from tb_sku ",
             "<foreach collection='ids' item='item' index='index' separator=',' open='where id in ( ' close=')' >",
             "#{item}",
             "</foreach>",
             "</script>"}) // #{ids}
    List<Sku> queryByIds(List<Long> ids);
}
