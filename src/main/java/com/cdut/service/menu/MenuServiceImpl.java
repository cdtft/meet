package com.cdut.service.menu;

import com.cdut.dao.mysql.menu.MenuRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.menu.MenuPO;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.vo.MenuOneLevelRespVO;
import com.cdut.vo.MenuReqVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by king on 2017/10/26.
 */
@Service
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    @Transactional
    public JsonResult saveMenu(MenuReqVO menuReqVO) {
        Long menuId = menuReqVO.getId();
        if (menuId == null) {
            MenuPO po = new MenuPO();
            po.setName(menuReqVO.getName());
            po.setIndexNum(menuReqVO.getIndexNum());
            MenuPO menuPO = menuRepository.save(po);
            logger.info("保存一级菜单");
            return new JsonResult(menuPO, "一级菜单保存成功", ResultStatus.SUCCESS);
        }
        MenuPO menuPO = menuRepository.findOne(menuId);
        List<MenuPO> childMenuPOList = menuPO.getChildMenu();
        if (childMenuPOList == null) {
            childMenuPOList = Lists.newArrayList();
        }
        MenuPO menuChildPO = new MenuPO();
        menuChildPO.setName(menuReqVO.getName());
        menuChildPO.setIndexNum(menuReqVO.getIndexNum());
        childMenuPOList.add(menuChildPO);
        menuPO.setChildMenu(childMenuPOList);
        MenuPO po = menuRepository.save(menuPO);
        logger.info("保存孩子菜单");
        return new JsonResult(menuReqVO, "孩子菜单保存成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult updateMenu(MenuReqVO vo) {
        Long menuId = vo.getId();
        MenuPO po = menuRepository.findOne(menuId);
        if (vo.getIndexNum() != null) {
            po.setIndexNum(vo.getIndexNum());
        }
        if (vo.getName() != null) {
            po.setName(vo.getName());
        }
        menuRepository.save(po);
        logger.info("{[]} 更新成功", po);
        return new JsonResult("菜单更新成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findOneLevelMenuAllChildMenuById(Long id) {
        MenuPO menuPOS = menuRepository.findOne(id);
        if (Objects.isNull(menuPOS)) {
            return new JsonResult("未能找到对应名称的菜单", ResultStatus.NOT_FOUND);
        }
        return new JsonResult(menuPOS, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findAllOneLevelMenu() {
        List<MenuOneLevelRespVO> oneLevelMenu = menuRepository.findOneLevelMenu();
        if (CollectionUtils.isEmpty(oneLevelMenu)) {
            logger.info("没有一级菜单");

            return new JsonResult(Lists.newArrayList(),"not found one level menu", ResultStatus.NO_CONTENT);
        }
        List<Long> menuIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(oneLevelMenu)) {
            menuIds = oneLevelMenu.stream().map(p -> Long.parseLong(p.getId().toString())).collect(Collectors.toList());
        }
        List<MenuPO> oneLevelMenuPoList = menuRepository.findByIdIn(menuIds);

        return new JsonResult(oneLevelMenuPoList, "OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult deleteMenuById(Long id) {
        menuRepository.delete(id);
        logger.info("menu id is [{}] deleted", id);
        return new JsonResult("菜单删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult checkMenuNameIsExist(String name) {
        MenuPO po = menuRepository.findByName(name);
        if (po == null) {
            return new JsonResult(false, "不存在", ResultStatus.SUCCESS);
        }
        return new JsonResult(true, "存在", ResultStatus.SUCCESS);
    }
}
