package com.changgou.goods.service.impl;

import com.changgou.goods.dao.AlbumMapper;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 10:37
 */
@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    /**
     * 多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(Album album, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(album);
        //执行搜索
        return new PageInfo<Album>(albumMapper.selectByExample(example));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Album>(albumMapper.selectAll());
    }

    /**
     * 条件查询
     *
     * @param album
     * @return
     */
    @Override
    public List<Album> findList(Album album) {
        //构建查询条件
        Example example = createExample(album);
        return albumMapper.selectByExample(example);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改
     *
     * @param album
     */
    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    /**
     * 增加album
     *
     * @param album
     */
    @Override
    public void add(Album album) {
        albumMapper.insert(album);
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public Album findById(Long id) {
        Album album = albumMapper.selectByPrimaryKey(id);
        return album;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Album> findAll() {
        List<Album> albumList = albumMapper.selectAll();
        return albumList;
    }

    /**
     * 构建查询条件
     *
     * @param album
     * @return
     */
    public Example createExample(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        if (album != null) {
            if (!StringUtils.isEmpty(album.getId())) {
                criteria.andEqualTo("id", album.getId());
            }
            if (!StringUtils.isEmpty(album.getTitle())) {
                criteria.andLike("title", "%" + album.getTitle() + "%");
            }
            if (!StringUtils.isEmpty(album.getImage())) {
                criteria.andLike("image", "%" + album.getImage() + "%");
            }
            if (!StringUtils.isEmpty(album.getImageItems())) {
                criteria.andLike("imageItems", "%" + album.getImageItems() + "%");
            }
        }
        return example;
    }
}
