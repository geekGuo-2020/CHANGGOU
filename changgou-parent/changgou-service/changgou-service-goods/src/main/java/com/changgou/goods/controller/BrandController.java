package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageInfo;
import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/26 20:40
 */
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 查询所有品牌
     * @return
     */
    @GetMapping
    public Result<Brand> findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result<Brand>(true, StatusCode.OK,"查询成功",brandList);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        //根据id查询
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true,StatusCode.OK,"查询成功",brand);
    }

    /**
     * 新增品牌数据
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改品牌数据
     * @param brand
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Brand brand,@PathVariable Integer id){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 多条件搜索品牌数据
     * @return
     */
    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand){
        List<Brand> list = brandService.findList(brand);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page,
                                     @PathVariable Integer size){
            //分页查询
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }


    /**
     * 多条件分页查询
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Brand brand,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size){
        //执行搜索
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

}
