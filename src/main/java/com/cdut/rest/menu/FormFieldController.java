package com.cdut.rest.menu;


import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.rest.BaseRestController;
import com.cdut.service.menu.UserInfoFormService;
import com.cdut.vo.FormFieldVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by king on 2017/10/31.
 */
@RestController
public class FormFieldController extends BaseRestController {

    @Autowired
    private UserInfoFormService userInfoFormService;

    @RequestMapping(value = "v1/api/form-fields", method = RequestMethod.GET)
    public JsonResult getAllFormField() {
        return userInfoFormService.findAllFormField();
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/form-fields", method = RequestMethod.POST)
    public JsonResult addFormField(@RequestBody FormFieldVO vo) {
        return userInfoFormService.addFormField(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/form-fields/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteFromFieldById(@PathVariable("id") Long id) {
        return userInfoFormService.deleteFormFieldById(id);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/form-fields", method = RequestMethod.PUT)
    public JsonResult updateForm(@RequestBody FormFieldVO vo) {
        return userInfoFormService.updateFormFieldById(vo);
    }

}
