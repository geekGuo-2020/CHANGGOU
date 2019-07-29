package com.changgou.goods.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 22:57
 */
public interface CategoryService {
    /**
     * category条件+分页查询
     * @param category
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Category category,Integer page,Integer size);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Category> findPage(Integer page, Integer size);

    /**
     * 条件查询
     * @param category
     * @return
     */
    List<Category> findList(Category category);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改
     * @param category
     */
    void update(Category category);

    /**
     * 新增
     * @param category
     */
    void add(Category category);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();

    /**
     * 根据父节点ID查询
     * @param id
     * @return
     */
    List<Category> findByParentId(Integer id);
}
