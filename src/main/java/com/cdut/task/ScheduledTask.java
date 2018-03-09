package com.cdut.task;

import com.cdut.dao.mysql.page.PictureRepository;
import com.cdut.entity.page.PicturePO;
import com.cdut.service.page.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xieqiang_daye on 2017/11/5.
 */
@Component
public class ScheduledTask {
    protected Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    @Autowired
    private PictureRepository pictureRepository;

    //暂时不自动删除
    /*@Scheduled(cron = "0 0 0 25 * ?")
    public  void reportCurrentTime() throws IOException{
        //隔固定的时间去查询数据库
        List<PicturePO> list  = pictureRepository.findAll();
        for (PicturePO po:list) {
            String filename = po.getUrl();
            filename = filename.substring(filename.lastIndexOf("/") + 1);
            //获取服务器的文件夹
            File directory = new File("");// 参数为空
            String path = directory.getCanonicalPath();
            path = path + "/src/main/webapp/static";
            System.out.println(path);
            System.out.println();
            File file = new File(path);
            if (!file.exists()) {
                logger.info("服务器文件夹不存在[{}]", file);
                break;
            }
            File[] files = file.listFiles();
            int j = 0;
            for (int i = 0; i < files.length; i++) {
                File picFile = files[i];
                String name = picFile.getName();
                if (name.equals(filename))
                    break;
                else
                    j = j + 1;
            }
            if (j == files.length) {
                File picUrl = new File(po.getUrl());
                picUrl.delete();
            }
        }
    }*/
}