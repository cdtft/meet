package com.cdut.rest.upload;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.vo.UploadRespVO;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xieqiang_daye on 2017/12/1.
 */
@RestController
public class Upload  extends BaseRestController{

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "/multiupload",method = RequestMethod.POST)
    public JsonResult upload(@RequestParam MultipartFile[] files, HttpServletRequest request)throws IOException{
        //获得webapp所在的路径
        String rootpath = request.getSession().getServletContext().getRealPath("/");
        String path ="";
        List<String> listImagePath=new ArrayList<String>();
        if(files.length>0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    //生成uuid作为文件名称
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    String fileName = file.getOriginalFilename();
                    //获得文件类型（可以判断如果不是图片，禁止上传）
                    String contentType = file.getContentType();
                    //获得文件后缀名称
                    String imageName = contentType.substring(contentType.indexOf("/") + 1);
                    path = "/static/" + uuid + "." + imageName;
                    file.transferTo(new File(rootpath + path));
                    listImagePath.add(path);
                }
            }
            return new JsonResult(listImagePath, "上传成功", ResultStatus.SUCCESS);
        }
        return new JsonResult("上传失败",ResultStatus.INVALID_REQUEST);
    }
}
