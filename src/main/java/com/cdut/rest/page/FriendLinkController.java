package com.cdut.rest.page;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.rest.BaseRestController;
import com.cdut.service.page.FriendLinkService;
import com.cdut.vo.FriendLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by king on 2017/11/6.
 */
@RestController
public class FriendLinkController extends BaseRestController {

    @Autowired
    private FriendLinkService friendLinkService;

    @Authorization
    @RoleRequired(role ="admin")
    @RequestMapping(value = "v1/api/friendLink", method = RequestMethod.POST)
    public JsonResult addFriendLink(@RequestBody FriendLinkVO vo) {
        return friendLinkService.addFriendLink(vo);
    }

    @Authorization
    @RoleRequired(role ="admin")
    @RequestMapping(value = "v1/api/friendLink", method = RequestMethod.PUT)
    public JsonResult updateFriendLink(@RequestBody FriendLinkVO vo) {
        return friendLinkService.updateFriendLink(vo);
    }

    @Authorization
    @RoleRequired(role ="admin")
    @RequestMapping(value = "v1/api/friendLink/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteFriendLink(@PathVariable("id") Long id) {
        return friendLinkService.deleteFriendLink(id);
    }

    @RequestMapping(value = "v1/api/friendLink", method = RequestMethod.GET)
    public JsonResult getAll() {
        return friendLinkService.findAll();
    }
}
