package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 21:08
 */
@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {
    @Autowired
    private SpecService specService;

    /**
     * spec分页条件搜索实现
     * @param spec
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Spec spec,
                                       @PathVariable Integer page,
                                       @PathVariable Integer size){
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
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
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        return  new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * 条件查询
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spec>> findList(@RequestBody(required = false) Spec spec){
        List<Spec> list = specService.findList(spec);
        return new Result<List<Spec>>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 删除根据id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        specService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 修改
     * @param spec
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spec spec,@PathVariable Integer id){
        //设置主键
        spec.setId(id);
        specService.update(spec);
        return  new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增数据
     * @param spec
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spec spec){
        specService.add(spec);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable Integer id){
        Spec spec = specService.findById(id);
        return new Result<Spec>(true,StatusCode.OK,"查询成功",spec);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public  Result<Spec> findAll(){
        List<Spec> list = specService.findAll();
        return new Result<Spec>(true,StatusCode.OK,"查询成功",list);
    }
}
