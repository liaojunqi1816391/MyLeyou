package com.leyou.item.dao;

import com.leyou.item.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SpuMapper extends Mapper<Spu> {
    @Update("update tb_spu set saleable = #{saleable} where id = #{id}")
    void changeSaleableById(@Param("id") Long spuId,@Param("saleable") Boolean saleable);
}
