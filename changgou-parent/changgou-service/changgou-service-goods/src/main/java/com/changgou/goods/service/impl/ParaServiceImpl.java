package com.changgou.goods.service.impl;

import com.changgou.goods.dao.ParaMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 21:45
 */
@Service
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;

    @Autowired
    private TemplateMapper templateMapper;

    /**
     * 条件+分页查询
     *
     * @param para
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Para> findPage(Para para, Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(para);
        //执行搜索
        return new PageInfo<Para>(paraMapper.selectByExample(example));
    }

    /**
     * 分页
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Para> findPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<Para>(paraMapper.selectAll());
    }

    /**
     * 条件查询
     *
     * @param para
     * @return
     */
    @Override
    public List<Para> findList(Para para) {
        //构建查询条件
        Example example = createExample(para);
        return paraMapper.selectByExample(example);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //根据id查询
        Para para = paraMapper.selectByPrimaryKey(id);
        //修改模板统计数据
        updateParaNum(para, -1);
        paraMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改
     *
     * @param para
     */
    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    /**
     * 增加
     *
     * @param para
     */
    @Override
    public void add(Para para) {
        paraMapper.insert(para);
    }

    /**
     * 根据id 查询
     *
     * @param id
     * @return
     */
    @Override
    public Para findById(Integer id) {
        Para para = paraMapper.selectByPrimaryKey(id);
        return para;
    }

    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    /**
     * 搜索条件对象
     *
     * @param para
     * @return
     */
    public Example createExample(Para para) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (para != null) {
            if (!StringUtils.isEmpty(para.getId())) {
                criteria.andEqualTo("id", para.getId());
            }
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name", "%" + para.getName() + "%");
            }
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andEqualTo("options", para.getOptions());
            }
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("seq", para.getSeq());
            }
            if (!StringUtils.isEmpty(para.getTemplateId())) {
                criteria.andEqualTo("templateId", para.getTemplateId());
            }
        }
        return example;
    }

    /**
     * 修改模板数据
     *
     * @param para
     * @param count
     */
    public void updateParaNum(Para para, Integer count) {
        Template template = templateMapper.selectByPrimaryKey(para.getId());
        template.setParaNum(template.getParaNum() + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }
}
