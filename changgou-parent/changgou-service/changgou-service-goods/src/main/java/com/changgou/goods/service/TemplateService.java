package com.changgou.goods.service;

import com.changgou.goods.pojo.Template;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 15:20
 */
public interface TemplateService {
    /**
     * Template多条件分页查询
     * @param template
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(Template template,Integer page,Integer size);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Template> findPage(Integer page,Integer size);

    /**
     * 多条件搜索
     * @param template
     * @return
     */
    List<Template> findList(Template template);

    /**
     * 删除Template
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 修改Template数据
     * @param template
     */
    void update(Template template);

    /**
     * 新增
     * @param template
     */
    void add(Template template);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Template findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<Template> findAll();
}
