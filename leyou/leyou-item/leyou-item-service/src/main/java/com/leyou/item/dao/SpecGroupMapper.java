package com.leyou.item.dao;

import com.leyou.item.pojo.SpecGroup;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SpecGroupMapper extends Mapper<SpecGroup> {
    @Update("update tb_spec_group set name = #{name} where id = #{id}")
    void updateBySpecGroup(SpecGroup specGroup);
}
