package com.changgou.goods.service.impl;

import com.changgou.entity.StatusCode;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/26 20:35
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 根据Id查询品牌
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
         return  brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增品牌
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改品牌数据
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 条件查询
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        //构建查询条件
        Example example = createExample(brand);
        //根据构建的条件查询数据
        return brandMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Brand>(brandMapper.selectAll());
    }

    /**
     * 多条件分页查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(brand);
        //执行搜索
        return new PageInfo<Brand>(brandMapper.selectByExample(example));
    }

    /**
     * 构建查询条件
     * @param brand
     * @return
     */
    private Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (brand!=null) {
            //品牌名称
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            //品牌图片地址
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andLike("image","%"+brand.getImage()+"%");
            }
            //品牌的首字母
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andLike("letter","%"+brand.getLetter()+"%");
            }
            //品牌id
            if (!StringUtils.isEmpty(brand.getId())) {
                criteria.andLike("id","%"+brand.getId()+"%");
            }
            //排序
            if (!StringUtils.isEmpty(brand.getSeq())) {
                criteria.andLike("seq","%"+brand.getSeq()+"%");
            }

        }
            return example;
    }
}
