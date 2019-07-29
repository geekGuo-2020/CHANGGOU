package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 15:42
 */
@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * template多条件分页查询
     *
     * @param template
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Template template,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size) {
        PageInfo<Template> pageInfo = templateService.findPage(template, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * 条件查询
     *
     * @return
     */
    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody(required = false) Template template) {
        List<Template> templateList = templateService.findList(template);
        return new Result<List<Template>>(true, StatusCode.OK, "查询成功", templateList);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改数据
     * @param id
     * @param template
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, Template template) {
        //设置主键
        template.setId(id);
        templateService.update(template);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增数据
     * @param template
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Template template){
        templateService.add(template);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id){
        Template template = templateService.findById(id);
        return new Result<Template>(true,StatusCode.OK,"查询成功",template);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<Template> findAll(){
        List<Template> templateList = templateService.findAll();
        return new Result<Template>(true,StatusCode.OK,"查询成功",templateList);
    }
}
