package com.cdut.service.menu;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.FormFieldVO;

/**
 * Created by king on 2017/10/31.
 */
public interface UserInfoFormService {

    JsonResult findAllFormField();

    JsonResult addFormField(FormFieldVO vo);

    JsonResult deleteFormFieldById(Long id);

    JsonResult updateFormFieldById(FormFieldVO vo);
}
