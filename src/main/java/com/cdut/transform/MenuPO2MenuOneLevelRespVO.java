package com.cdut.transform;

import com.cdut.entity.menu.MenuPO;
import com.cdut.vo.MenuOneLevelRespVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/10/27.
 */
public class MenuPO2MenuOneLevelRespVO implements Function<MenuPO, MenuOneLevelRespVO> {

    @Nullable
    @Override
    public MenuOneLevelRespVO apply(@Nullable MenuPO input) {
        if (input != null) {
            MenuOneLevelRespVO vo = new MenuOneLevelRespVO();
            vo.setId(input.getId());
            vo.setIndexNm(input.getIndexNum());
            vo.setName(input.getName());
            return vo;
        }
        return null;
    }
}
