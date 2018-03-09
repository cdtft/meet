package com.cdut.rest.page;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.rest.BaseRestController;
import com.cdut.service.page.SideDateTimeService;
import com.cdut.vo.SideTimeRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对事件
 * Created by king on 2017/10/29.
 */
@RestController
public class SideDateTimeController extends BaseRestController {

    @Autowired
    private SideDateTimeService sideDateTimeService;

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/dateTime", method = RequestMethod.POST)
    public JsonResult saveDateTime(@RequestBody SideTimeRespVO vo) {
        return sideDateTimeService.saveDateTime(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/dateTime/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteDateTime(@PathVariable("id") Long id) {
        return sideDateTimeService.deleteDateTimeById(id);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/dateTime", method = RequestMethod.PUT)
    public JsonResult updateDateTime(@RequestBody SideTimeRespVO vo) {
        return sideDateTimeService.updateDateTime(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/dateTime", method = RequestMethod.GET)
    public JsonResult getAllDateTime() {
        return sideDateTimeService.getAll();
    }
}
