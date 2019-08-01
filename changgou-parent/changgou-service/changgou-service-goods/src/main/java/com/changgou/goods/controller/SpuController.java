package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 郭师兄
 * @Date: 2019/7/31 18:01
 */
@RestController
@RequestMapping("/spu")
@CrossOrigin
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 批量上架
     * @param ids
     * @return
     */
    @PutMapping("/put/many")
    public Result putMany(@RequestBody Long[] ids){
        int count = spuService.putMany(ids);
        return new Result(true,StatusCode.OK,"上架"+count+"个商品");
    }
    /**
     * 商品上架
     * @param spuId
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable(value = "id") Long spuId){
        spuService.put(spuId);
        return new Result(true,StatusCode.OK,"上架成功");
    }
    /**
     * 商品下架
     * @param spuId
     * @return
     */
    @PutMapping("/pull/{id}")
    public  Result pull(@PathVariable(value = "id") Long spuId){
        spuService.pull(spuId);
        return new Result(true,StatusCode.OK,"下架成功");
    }
    /**
     * 审核商品
     * @param spuId
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable(value = "id") Long spuId){
        spuService.audit(spuId);
        return new Result(true,StatusCode.OK,"审核成功");
    }

    /**
     * 根据spu的id查询Goods数据
     * @param id
     * @return
     */
    @GetMapping("/goods/{id}")
    public Result<Goods> findGoodsById(@PathVariable(value = "id")Long id){
        Goods goods = spuService.findGoodsBySpuId(id);
        return new Result<Goods>(true,StatusCode.OK,"查询成功",goods);
    }

    /**保存商品
     * 添加goods
     * @param goods
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return new Result(true, StatusCode.OK,"保存成功");
    }

    /**
     * 逻辑删除
     * @param spuId
     * @return
     */
    @PutMapping("/logic/delete/{id}")
    public Result logicDelete(@PathVariable(value = "id") Long spuId){
        spuService.logicDelete(spuId);
        return new Result(true,StatusCode.OK,"逻辑删除成功");
    }

    /**
     * 恢复数据
     * @param spuId
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable(value = "id") Long spuId){
        spuService.restore(spuId);
        return new Result(true,StatusCode.OK,"数据恢复成功");
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable(value = "id") Long spuId){
       spuService.delete(spuId);
       return  new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * Spu分页条件搜索实现
     * @param spu
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) Spu spu, @PathVariable  int page, @PathVariable  int size){
        //调用SpuService实现分页条件查询Spu
        PageInfo<Spu> pageInfo = spuService.findPage(spu, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }
}
