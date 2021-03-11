package com.leyou.item.dao;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface BrandMapper  extends Mapper<Brand> {
    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);


    @Select("select category_id from tb_category_brand where brand_id=#{id}")
    Set<Long> selectCidsByPrimaryKey(Long id);

    @Delete({
            "<script>",
            "delete from tb_category_brand where brand_id=#{bid} " +
            "and category_id not in",
            "<foreach collection='cids' item='item' open='(' close=')' separator=','>",
            "#{item}",
            "</foreach>",
            "</script>"
        })
    void deleteCids(@Param("bid") Long id,@Param("cids") List<Long> cids);

    @Delete("delete from tb_category_brand where brand_id=#{bid}")
    void deleteCategoryAndBrandByBid(Long bid);

    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}
