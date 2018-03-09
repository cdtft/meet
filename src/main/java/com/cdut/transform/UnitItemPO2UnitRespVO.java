package com.cdut.transform;

import com.cdut.entity.page.UnitItemPO;
import com.cdut.vo.UnitItemRespVO;
import com.cdut.vo.UnitRespVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/10/26.
 */
public class UnitItemPO2UnitRespVO implements Function<UnitItemPO, UnitItemRespVO> {

    @Nullable
    @Override
    public UnitItemRespVO apply(@Nullable UnitItemPO input) {
        if (input != null) {
            UnitItemRespVO vo = new UnitItemRespVO();
            vo.setIndexNum(input.getIndexNum());
            vo.setName(input.getName());
            vo.setId(input.getId());
            return vo;
        }
        return null;
    }
}
