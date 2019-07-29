package com.changgou.goods.service;

import com.changgou.entity.Page;
import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 10:36
 */
public interface AlbumService {
    /**
     * 多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Album album, Integer page, Integer size);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Integer page, Integer size);

    /**
     * 多条件搜索方法
     *
     * @param album
     * @return
     */
    List<Album> findList(Album album);

    /**
     * 删除Album
     *
     * @param id
     */
    public void delete(Long id);

    /**
     * 修改Album数据
     *
     * @param album
     */
    public void update(Album album);

    /**
     * 新增Album
     *
     * @param album
     */
    public void add(Album album);

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    Album findById(Long id);

    /**
     * 查询所有Album
     *
     * @return
     */
    List<Album> findAll();
}
