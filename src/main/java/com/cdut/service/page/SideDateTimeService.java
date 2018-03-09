package com.cdut.service.page;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.SideTimeRespVO;

/**
 * Created by king on 2017/10/29.
 */
public interface SideDateTimeService {

    JsonResult saveDateTime(SideTimeRespVO vo);

    JsonResult deleteDateTimeById(Long id);

    JsonResult updateDateTime(SideTimeRespVO vo);

    JsonResult getAll();
}
