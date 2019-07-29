package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 21:45
 */
@RestController
@RequestMapping("/para")
@CrossOrigin
public class ParaController {
    @Autowired
    private ParaService paraService;

    /**
     * 条件+分页查询
     * @param para
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Para para,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size){
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return  new Result<PageInfo>(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public  Result<PageInfo> findPage(@PathVariable Integer page,@PathVariable Integer size){
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return  new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * 多条件查询
     * @param para
     * @return
     */
    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody(required = false) Para para){
        List<Para> list = paraService.findList(para);
        return new Result<List<Para>>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        paraService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 修改
     * @param para
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Para para,@PathVariable Integer id){
        para.setId(id);
        //修改数据
        paraService.update(para);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增
     * @param para
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Para para){
        paraService.add(para);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable Integer id ){
        Para para = paraService.findById(id);
        return new Result<Para>(true, StatusCode.OK,"查询成功",para);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<Para> findAll(){
        List<Para> list = paraService.findAll();
        return  new Result<Para>(true,StatusCode.OK,"查询成功",list);
    }
}
