package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 11:52
 */
@RestController
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /**
     * 多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Album album,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size) {
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
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
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * 多条件搜索方法
     *
     * @param album
     * @return
     */
    @PostMapping("/search")
    public Result<List<Album>> findList(@RequestBody(required = false) Album album) {
        List<Album> albumList = albumService.findList(album);
        return new Result<List<Album>>(true, StatusCode.OK, "查询成功", albumList);
    }

    /**
     * 删除Album
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改Album数据
     *
     * @param album
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Album album) {
        //设置主键
        album.setId(id);
        //修改数据
        albumService.update(album);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 新增Album
     *
     * @param album
     */
    @PostMapping
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return new Result<Album>(true,StatusCode.OK,"查询成功",album);
    }

    /**
     * 查询所有Album
     *
     * @return
     */
    @GetMapping
    public Result<List<Album>> findAll() {
        List<Album> albumList = albumService.findAll();
        return new Result<List<Album>>(true,StatusCode.OK,"查询成功",albumList);

    }
}
