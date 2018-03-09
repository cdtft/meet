package com.cdut.service.menu;

import com.cdut.entity.common.JsonResult;
import com.cdut.entity.menu.MenuPO;
import com.cdut.vo.MenuReqVO;

/**
 * Created by king on 2017/10/26.
 */
public interface MenuService {

    JsonResult saveMenu(MenuReqVO vo);

    JsonResult updateMenu(MenuReqVO vo);

    JsonResult findOneLevelMenuAllChildMenuById(Long name);

    JsonResult findAllOneLevelMenu();

    JsonResult deleteMenuById(Long id);

    JsonResult checkMenuNameIsExist(String name);
}
