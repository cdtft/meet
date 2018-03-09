package com.cdut.service.unit;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.UnitItemReqVO;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
public interface UnitItemService {

    JsonResult saveUnitItemByUnitId(Long id, UnitItemReqVO vo);

    JsonResult deleteUnitItemById(Long id);

    JsonResult updateUnitItem(UnitItemReqVO vo);
}
