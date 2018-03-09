package com.cdut.transform;

import com.cdut.entity.page.SideTimePO;
import com.cdut.vo.SideTimeRespVO;
import com.google.common.base.Function;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Created by king on 2017/10/26.
 */
public class SideTimePO2SideTimeRespVO implements Function<SideTimePO, SideTimeRespVO> {

    @Nullable
    @Override
    public SideTimeRespVO apply(@Nullable SideTimePO input) {

        if (Objects.nonNull(input)) {
            SideTimeRespVO sideTimeRespVO = new SideTimeRespVO();
            BeanUtils.copyProperties(input, sideTimeRespVO);
            return sideTimeRespVO;
        }
        return null;
    }
}
