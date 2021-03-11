package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryCategoriesByPid(Long pid)throws Exception;

    List<Category> queryByBrandId(Long bid);

    List<String> queryNamesByIds(List<Long> ids);

    List<Category> queryAllByCid3(Long id);
}
