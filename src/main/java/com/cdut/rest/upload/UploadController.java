package com.cdut.rest.upload;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PicturePO;
import com.cdut.enums.PictureTypeEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.service.page.PictureService;
import com.cdut.vo.PictureVO;
import com.cdut.vo.UploadRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文章、图片上传控制器
 * Created by king on 2017/10/27.
 */
@RestController
public class UploadController extends BaseRestController {

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonResult upload(@RequestParam MultipartFile file, HttpServletRequest request) {

        if (file != null) {
            String path = request.getSession().getServletContext().getRealPath("/") + "/static/";
            String fileName = file.getOriginalFilename();
            logger.info("图片的名称是：", fileName);
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String fileAlias = UUID.randomUUID() + fileType;
            File targetFile = new File(path,fileAlias);
            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
                return new JsonResult("上传失败", ResultStatus.SERVICE_ERROR);
            }
            StringBuilder builder = new StringBuilder();
            builder.append("/static/");
            builder.append(fileAlias);
            UploadRespVO vo = new UploadRespVO();
            vo.setFileName(fileName);
            vo.setUrl(builder.toString());
            return new JsonResult(vo, "上传成功", ResultStatus.SUCCESS);
        }
        return new JsonResult("参数错误", ResultStatus.INVALID_REQUEST);
    }
}
