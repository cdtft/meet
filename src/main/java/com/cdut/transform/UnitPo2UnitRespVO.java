package com.cdut.transform;

import com.cdut.entity.page.UnitPO;
import com.cdut.vo.UnitRespVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/10/28.
 */
public class UnitPo2UnitRespVO implements Function<UnitPO, UnitRespVO> {
    @Nullable
    @Override
    public UnitRespVO apply(@Nullable UnitPO unitPO) {
        if (unitPO != null) {

            UnitRespVO vo = new UnitRespVO();
            vo.setName(unitPO.getName());
            vo.setIndexNum(unitPO.getIndexNum());
            vo.setId(unitPO.getId());
            return vo;
        }
        return null;
    }
}
