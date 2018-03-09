package com.cdut.service.unit;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.UnitReqVO;
import com.cdut.vo.UnitRespVO;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
public interface UnitService {

    JsonResult save(UnitReqVO unitReqVO);

    JsonResult deleteUnitById(Long id);

    JsonResult updateUnitName(UnitRespVO unitRespVO);

    JsonResult getAllUnit();

    JsonResult getUnitItemById(Long id);
}
