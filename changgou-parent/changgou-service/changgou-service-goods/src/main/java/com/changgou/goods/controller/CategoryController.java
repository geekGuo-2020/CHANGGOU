package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 22:58
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 条件+分页查询
     * @param category
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Category category,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size){
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",pageInfo);
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
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /**
     * 条件查询
     * @param category
     * @return
     */
    @PostMapping("/search")
    public Result<List<Category>> findList(@RequestBody(required = false) Category category){
        List<Category> list = categoryService.findList(category);
        return new Result<List<Category>>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 修改
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Category category,@PathVariable Integer id){
        category.setId(id);
        categoryService.update(category);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增
     * @param category
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     *  根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable Integer id){
        Category category = categoryService.findById(id);
        return new Result<Category>(true,StatusCode.OK,"查询成功",category);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<List<Category>> findAll(){
        List<Category> list = categoryService.findAll();
        return new Result<List<Category>>(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 根据父iD查询
     * @param pid
     * @return
     */
    @RequestMapping("/list/{pid}")
    public Result<Category> findByParentId(@PathVariable(value = "pid") Integer pid){
        List<Category> list = categoryService.findByParentId(pid);
        return  new Result<Category>(true,StatusCode.OK,"查询成功",list);
    }
}
