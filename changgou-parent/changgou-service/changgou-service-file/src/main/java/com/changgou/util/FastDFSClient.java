package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;


/**
 *  实现FastDFS 文件上传,下载功能
 * @author: 郭师兄
 * @date: 2019/7/27 19:21
 */
public class FastDFSClient {
    /**
     * 初始化tracker信息
     */
    static{
        try {
            //获取tracker的配置文件fdfd_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            //加载读取fdfs_client配置文件内容
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 文件上传
     * @param file :FastDFSFile要上传的文件信息封装
     * @return String[]
     *      1.文件上传所存储的组名
     *      2.文件存储路径
     */
    public static String[] upload(FastDFSFile file){
        //上传后的返回数据
        String[] uploadResults=null;
        try {
            //附加数据
            NameValuePair[] mata_list = new NameValuePair[1];
            mata_list[0]=new NameValuePair("author",file.getAuthor());

            //创建一个TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //连接trackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过trackerServer来获取storage信息,将storage信息封装到一个storageClient中
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //通过StorageClient操作Storage (文件上传),并获取返回的文件上传信息
         /**返回数据:
          *        1.当前文件所存储的storage的组
          *        2.文件存储路径
          * */

            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), mata_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResults;
    }

}
