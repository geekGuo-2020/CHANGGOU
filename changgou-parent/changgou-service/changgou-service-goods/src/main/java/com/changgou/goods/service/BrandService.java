package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/26 20:35
 */

public interface BrandService {
    /**
     * 查讯所有品牌
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    Brand findById(Integer id);

    /**
     * 新增品牌
     * @param brand
     */
    void add(Brand brand);

    /**
     * 修改品牌数据
     * @param brand
     */
    void update(Brand brand);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 多条件搜索品牌
     * @param brand
     * @return
     */
    List<Brand> findList(Brand brand);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Integer page, Integer size);

    /**
     * 多条件分页查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);

}
