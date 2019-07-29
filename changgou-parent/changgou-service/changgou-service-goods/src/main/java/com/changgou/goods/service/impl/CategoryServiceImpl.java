package com.changgou.goods.service.impl;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 22:57
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 条件+分页查询
     * @param category
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Category> findPage(Category category, Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(category);
        return new PageInfo<Category>(categoryMapper.selectByExample(example));
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Category> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return new PageInfo<Category>(categoryMapper.selectAll());
    }

    /**
     * 条件查询
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        Example example = createExample(category);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 新增
     * @param category
     */
    @Override
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    /**
     * 根据父节点查询
     * @param pid
     * @return
     */
    @Override
    public List<Category> findByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    /**
     * 构建查询对象
     * @param category
     * @return
     */
    public Example createExample(Category category){
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (category!=null) {
            //分类id
            if (!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id",category.getId());
            }
            //分类名称
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name","%"+category.getName()+"%");
            }
            //商品数量
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goodsNum",category.getGoodsNum());
            }
            //是否显示
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andEqualTo("isShow",category.getName());
            }
            //是否导航
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andEqualTo("isMenu",category.getIsMenu());
            }
            //排序
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("seq",category.getSeq());
            }

            if (!StringUtils.isEmpty(category.getParentId())) {
                criteria.andEqualTo("parentId",category.getParentId());
            }

            if (!StringUtils.isEmpty(category.getTemplateId())) {
                criteria.andEqualTo("templateId",category.getTemplateId());
            }
        }
        return example;
    }
}
