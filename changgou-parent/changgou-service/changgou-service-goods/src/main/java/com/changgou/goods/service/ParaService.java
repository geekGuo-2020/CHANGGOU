package com.changgou.goods.service;

import com.changgou.goods.pojo.Para;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 21:44
 */
public interface ParaService {
    /**
     * para 条件+分页查询
     * @param para
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Para para,Integer page, Integer size);

    /**
     * para 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Para> findPage(Integer page, Integer size);

    /**
     * 条件查询
     * @param para
     * @return
     */
    List<Para> findList(Para para);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改
     * @param para
     */
    void update(Para para);

    /**
     * 新增
     * @param para
     */
    void add(Para para);

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    Para findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<Para> findAll();

}
