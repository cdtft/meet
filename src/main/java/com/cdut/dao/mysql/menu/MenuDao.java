package com.cdut.dao.mysql.menu;

import com.cdut.vo.MenuOneLevelRespVO;

import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
public interface MenuDao {

    /**
     * 查询所有的一级菜单
     * @return
     */
    List<MenuOneLevelRespVO> findOneLevelMenu();
}
