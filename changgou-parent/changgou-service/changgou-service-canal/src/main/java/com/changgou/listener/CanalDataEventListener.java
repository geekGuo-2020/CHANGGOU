package com.changgou.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * @Author: 郭师兄
 * @Date: 2019/7/30 19:39
 */
@CanalEventListener  //Canal数据监听
public class CanalDataEventListener {

    /**
     * 增加数据监听
     * @param eventType 操作类型,增删改
     * @param rowData   操作的数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
//        rowData.getAfterColumnsList():获取所有列操作后的信息
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("增加--->列名:"+column.getName()+"="+column.getValue());
        }
    }
    /**
     * 修改数据监听
     */
    @UpdateListenPoint
    public void onEventeUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
//        rowData.getAfterColumnsList():获取所有列操作后的信息
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("修改--->列名:"+column.getName()+"="+column.getValue());
        }
    }


    /**
     * 删除数据监听
     */
    @DeleteListenPoint
    public void onEventeDelete(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
//        rowData.getAfterColumnsList():获取所有列操作后的信息
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("删除--->列名:"+column.getName()+"="+column.getValue());
        }
    }


    /**
     * 自定义数据修改监听
     */
    @ListenPoint(destination = "example",schema = "changgou_content",table = {"tb_content","tb_category"},eventType = CanalEntry.EventType.UPDATE)
    public void onEventCustomUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
//        rowData.getAfterColumnsList():获取所有列操作后的信息
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("修改--->列名:"+column.getName()+"="+column.getValue());
        }
    }
}
