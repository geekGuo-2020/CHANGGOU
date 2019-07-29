package com.changgou.goods.service.impl;

import com.changgou.entity.Result;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 15:21
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    /**
     * Template多条件+分页查询
     *
     * @param template
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(Template template, Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(template);
        //执行搜索
        return new PageInfo<Template>(templateMapper.selectByExample(example));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Template>(templateMapper.selectAll());
    }

    /**
     * 条件查询
     *
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        //构建查询条件
        Example example = createExample(template);
        //根据构建的条件查询数据
        return templateMapper.selectByExample(example);
    }

    /**
     * 删除 Template
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Template
     *
     * @param template
     */
    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    /**
     * 增加
     *
     * @param template
     */
    @Override
    public void add(Template template) {
        templateMapper.insertSelective(template);
    }

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {
        Template template = templateMapper.selectByPrimaryKey(id);
        return template;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Template> findAll() {

        return templateMapper.selectAll();
    }


    /**
     * Template构建查询对象
     *
     * @param template
     * @return
     */
    public Example createExample(Template template) {

        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template != null) {
            //id
            if (StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id", template.getId());
            }
            //模板名称
            if (StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            //规格数量
            if (StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
            //参数数量
            if (StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum", template.getParaNum());
            }
        }
        return example;
    }
}
