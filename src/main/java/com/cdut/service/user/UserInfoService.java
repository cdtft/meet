package com.cdut.service.user;

import com.cdut.entity.common.JsonResult;
import com.cdut.entity.user.User;
import com.cdut.vo.PageVO;
import com.cdut.vo.UserInfoVO;

/**
 * Created by king on 2017/10/31.
 */
public interface UserInfoService {

    JsonResult save(String info, Long id);

    JsonResult deleteByUserInfoId(Long userId);

    JsonResult findAll(PageVO vo);

    JsonResult findUserInfoByUserId(Long id);
   
}
