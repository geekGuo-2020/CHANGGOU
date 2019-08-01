package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

/**
 * @Author: 郭师兄
 * @Date: 2019/7/31 16:41
 */
public interface SpuService {


    /**
     * 商品审核
     *   修改status审核状态
     *   修改ismarketTable
     */
    void audit(Long spuId);

    /**
     * 商品下架
     * @param spuId
     */
    void pull(Long spuId);

    /**
     * 商品上架
     * @param spuId
     */
    void put(Long spuId);

    /**
     * 批量上架
     * @param Ids
     */
    Integer putMany(Long[] Ids);

    /**
     * 根据Spu的id查询spu和list<Sku>的信息
     * @param spuId
     */
    Goods findGoodsBySpuId(Long spuId);


    /**
     * 保存商品
     * @param goods
     */
    void saveGoods(Goods goods);

    /**
     * 逻辑删除
     * @param spuId
     */
    void logicDelete(Long spuId);

    /**
     * 还原被删除商品
     * @param spuId
     */
    void restore(Long spuId);

    /**
     * 物理删除
     * @param spuId
     */
    void delete(Long spuId);

    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);
}
