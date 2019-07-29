package com.changgou.goods.service.impl;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: 郭师兄
 * @date: 2019/7/28 11:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumServiceImplTest {
    @Autowired
    private AlbumServiceImpl albumService;
    @Test
    public void findPage() {
        Album album = new Album();
        album.setTitle("华");
        PageInfo<Album> page = albumService.findPage(album, 1, 2);
        System.out.println(page);

    }

}