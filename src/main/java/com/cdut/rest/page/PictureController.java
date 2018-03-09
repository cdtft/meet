package com.cdut.rest.page;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.rest.BaseRestController;
import com.cdut.service.page.PictureService;
import com.cdut.vo.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by king on 2017/10/29.
 */
@RestController
public class PictureController extends BaseRestController {

    @Autowired
    private PictureService pictureService;

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/picture", method = RequestMethod.POST)
    public JsonResult savePicture(@RequestBody PictureVO vo) {
        return pictureService.addPicture(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/picture", method = RequestMethod.PUT)
    public JsonResult updatePicture(@RequestBody PictureVO vo) {
        return pictureService.updatePicture(vo);
    }


    @RequestMapping(value = "v1/api/picture", method = RequestMethod.GET)
    public JsonResult getAllPicture() {
        return pictureService.findAll();
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/picture/{id}", method = RequestMethod.DELETE)
    public JsonResult deletePictureById(@PathVariable("id") Long id) {
        return pictureService.deletePicture(id);
    }
}
