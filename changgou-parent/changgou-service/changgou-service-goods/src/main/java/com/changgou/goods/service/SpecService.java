package com.changgou.goods.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 20:13
 */
public interface SpecService {
    /**
     * Spec多条件分页查询
     *
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Spec spec, Integer page, Integer size);

    /**
     * Spec分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Integer page, Integer size);

    /**
     * Spec多条件搜索
     *
     * @param spec
     * @return
     */
    List<Spec> findList(Spec spec);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改Spec数据
     *
     * @param spec
     */
    void update(Spec spec);

    /**
     * 新增Spec
     *
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据id查询Spec
     *
     * @param id
     * @return
     */
    Spec findById(Integer id);

    /**
     * 查询所有Spec
     *
     * @return
     */
    List<Spec> findAll();
}
