package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.IdWorker;
import com.changgou.goods.dao.*;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 郭师兄
 * @Date: 2019/7/31 16:42
 */
@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 商品审核
     * @param spuId
     */
    @Override
    public void audit(Long spuId) {
        //查询商品
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //判断是否已删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("只能删除为审核的商品");
        }
        //未删除,修改审核状态为上架状态1
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品下架
     * @param spuId
     */
    @Override
    public void pull(Long spuId) {
        //查询商品数据
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断是否删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品已删除,无法下架");
        }
        //修改为下架状态
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品上架
     * @param spuId
     */
    @Override
    public void put(Long spuId) {

        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("商品已删除,无法上架");
        }
        if (!spu.getStatus().equalsIgnoreCase("1")) {
            throw  new RuntimeException("审核未通过");
        }
        //审核通过
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 批量上架
     * @param Ids
     */
    @Override
    public Integer putMany(Long[] Ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("1");
        //批量修改

        //构建条件Example
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(Ids));
        //下架
        criteria.andEqualTo("isMarketable","0");
        criteria.andEqualTo("status","1");
        criteria.andEqualTo("isDelete",0);
        return spuMapper.updateByExampleSelective(spu,example);

    }


    /**
     * 根据spu的id 查询Goods
     * @param spuId
     * @return
     */
    @Override
    public Goods findGoodsBySpuId(Long spuId) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //List<Sku>
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());

        List<Sku> skuList = skuMapper.select(sku);
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /**
     * 保存商品
     * @param goods
     */
    @Override
    public void saveGoods(Goods goods) {
        //增加Spu
        Spu spu = goods.getSpu();
        if (spu.getId()==null) {
            //增加
            //Spu的id不是自增,需要设置值
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        }else {
            //修改spu
            spuMapper.updateByPrimaryKeySelective(spu);
            //删除之前的list<Sku>
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        //增加Sku,循环保存
        //3级分类
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        //品牌数据
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        Date date = new Date();
        //获取SkU集合
        List<Sku> skuList = goods.getSkuList();
        if (skuList!=null&& skuList.size()>0) {
            //循环将数据加入数据库
            for (Sku sku : skuList) {
                //id
                sku.setId(idWorker.nextId());

                //判断spec是否为空
                if (StringUtils.isEmpty(sku.getSpec())) {
                    sku.setSpec("{}");
                }
                //sku name
                String name = spu.getName();
                //获取规格
                Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                for (Map.Entry<String, String> entry : specMap.entrySet()) {
                    name+="  "+entry.getValue();
                }
                sku.setName(name);
                //创建时间
                sku.setCreateTime(date);
                //更新时间
                sku.setUpdateTime(date);
                //spu id
                sku.setSpuId(spu.getId());
                //类目id
                sku.setCategoryId(spu.getCategory3Id());
                //类目名称
                sku.setCategoryName(category.getName());
                //品牌名称
                sku.setBrandName(brand.getName());
                //商品状态
                sku.setStatus("1");
                //增加数据
               skuMapper.insertSelective(sku);
            }
            //品牌分类的关联
            CategoryBrand categoryBrand = new CategoryBrand();
            categoryBrand.setCategoryId(spu.getCategory3Id());
            categoryBrand.setBrandId(spu.getBrandId());
            int count = categoryBrandMapper.selectCount(categoryBrand);
            if (count==0) {
                categoryBrandMapper.insertSelective(categoryBrand);

            }
        }
    }

    /**
     * 逻辑删除
     * @param spuId
     */
    @Override
    public void logicDelete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否是下架的商品
        if (!spu.getIsMarketable().equalsIgnoreCase("0")) {
            throw new RuntimeException("必须先下架再删除");
        }
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("已删除,不能再删除");
        }
        //删除
        spu.setIsDelete("1");
        //未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 还原被删除商品
     * @param spuId
     */
    @Override
    public void restore(Long spuId) {
        //查询
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否已删除
        if (!spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品未删除");
        }
        spu.setIsDelete("0");
        spu.setStatus("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**'
     * 物理删除
     * @param spuId
     */
    @Override
    public void delete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否被逻辑删除, 必须先逻辑删除后才能物理删除
        if (!spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品不能删除");
        }
        spuMapper.deleteByPrimaryKey(spu);
    }

    /**
     * Spu条件+分页查询
     * @param spu 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }


    /**
     * Spu构建查询对象
     * @param spu
     * @return
     */
    public Example createExample(Spu spu){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(spu!=null){
            // 主键
            if(!StringUtils.isEmpty(spu.getId())){
                criteria.andEqualTo("id",spu.getId());
            }
            // 货号
            if(!StringUtils.isEmpty(spu.getSn())){
                criteria.andEqualTo("sn",spu.getSn());
            }
            // SPU名
            if(!StringUtils.isEmpty(spu.getName())){
                criteria.andLike("name","%"+spu.getName()+"%");
            }
            // 副标题
            if(!StringUtils.isEmpty(spu.getCaption())){
                criteria.andEqualTo("caption",spu.getCaption());
            }
            // 品牌ID
            if(!StringUtils.isEmpty(spu.getBrandId())){
                criteria.andEqualTo("brandId",spu.getBrandId());
            }
            // 一级分类
            if(!StringUtils.isEmpty(spu.getCategory1Id())){
                criteria.andEqualTo("category1Id",spu.getCategory1Id());
            }
            // 二级分类
            if(!StringUtils.isEmpty(spu.getCategory2Id())){
                criteria.andEqualTo("category2Id",spu.getCategory2Id());
            }
            // 三级分类
            if(!StringUtils.isEmpty(spu.getCategory3Id())){
                criteria.andEqualTo("category3Id",spu.getCategory3Id());
            }
            // 模板ID
            if(!StringUtils.isEmpty(spu.getTemplateId())){
                criteria.andEqualTo("templateId",spu.getTemplateId());
            }
            // 运费模板id
            if(!StringUtils.isEmpty(spu.getFreightId())){
                criteria.andEqualTo("freightId",spu.getFreightId());
            }
            // 图片
            if(!StringUtils.isEmpty(spu.getImage())){
                criteria.andEqualTo("image",spu.getImage());
            }
            // 图片列表
            if(!StringUtils.isEmpty(spu.getImages())){
                criteria.andEqualTo("images",spu.getImages());
            }
            // 售后服务
            if(!StringUtils.isEmpty(spu.getSaleService())){
                criteria.andEqualTo("saleService",spu.getSaleService());
            }
            // 介绍
            if(!StringUtils.isEmpty(spu.getIntroduction())){
                criteria.andEqualTo("introduction",spu.getIntroduction());
            }
            // 规格列表
            if(!StringUtils.isEmpty(spu.getSpecItems())){
                criteria.andEqualTo("specItems",spu.getSpecItems());
            }
            // 参数列表
            if(!StringUtils.isEmpty(spu.getParaItems())){
                criteria.andEqualTo("paraItems",spu.getParaItems());
            }
            // 销量
            if(!StringUtils.isEmpty(spu.getSaleNum())){
                criteria.andEqualTo("saleNum",spu.getSaleNum());
            }
            // 评论数
            if(!StringUtils.isEmpty(spu.getCommentNum())){
                criteria.andEqualTo("commentNum",spu.getCommentNum());
            }
            // 是否上架
            if(!StringUtils.isEmpty(spu.getIsMarketable())){
                criteria.andEqualTo("isMarketable",spu.getIsMarketable());
            }
            // 是否启用规格
            if(!StringUtils.isEmpty(spu.getIsEnableSpec())){
                criteria.andEqualTo("isEnableSpec",spu.getIsEnableSpec());
            }
            // 是否删除
            if(!StringUtils.isEmpty(spu.getIsDelete())){
                criteria.andEqualTo("isDelete",spu.getIsDelete());
            }
            // 审核状态
            if(!StringUtils.isEmpty(spu.getStatus())){
                criteria.andEqualTo("status",spu.getStatus());
            }
        }
        return example;
    }
}
