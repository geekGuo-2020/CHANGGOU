package com.changgou.goods.dao;

import com.changgou.goods.pojo.Album;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 10:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumMapperTest {
    @Autowired
    private AlbumMapper albumMapper;

    /**
     * 查询所有
     */
    @Test
    public void findAll() {
        List<Album> albumMapperList = albumMapper.selectAll();
        Assert.assertNotNull(albumMapperList);
    }
}