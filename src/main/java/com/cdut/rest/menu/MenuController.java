package com.cdut.rest.menu;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.menu.MenuPO;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.service.menu.MenuService;
import com.cdut.vo.MenuReqVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
@RestController
public class MenuController extends BaseRestController {

    @Autowired
    private MenuService menuService;

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/menu", method = RequestMethod.POST)
    public JsonResult saveMenu(@RequestBody MenuReqVO vo) {

        logger.info("添加菜单vo [{}]", vo);
        return menuService.saveMenu(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/menu", method = RequestMethod.PUT)
    public JsonResult update(@RequestBody MenuReqVO vo) {
        return menuService.updateMenu(vo);
    }

    @RequestMapping(value = "v1/api/menu/oneLevel", method = RequestMethod.GET)
    public JsonResult getAllOneLevelMenu() {

        return menuService.findAllOneLevelMenu();
    }

    @RequestMapping(value = "v1/api/menus/{id}", method = RequestMethod.GET)
    public JsonResult getChildMenuByOneLevelMenuName(@PathVariable("id") Long id) {

        return menuService.findOneLevelMenuAllChildMenuById(id);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/menu/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteMenu(@PathVariable("id") Long id) {
        logger.info("删除菜单id[{}]", id);
        return menuService.deleteMenuById(id);
    }

    @RequestMapping(value = "v1/api/menu/{menuName}", method = RequestMethod.GET)
    public JsonResult checkMenuNameIsExist(@PathVariable("menuName") String menuName) {
        if (StringUtils.isBlank(menuName)) {
            return new JsonResult("menuName为空", ResultStatus.INVALID_REQUEST);
        }
        return menuService.checkMenuNameIsExist(menuName);
    }


}
