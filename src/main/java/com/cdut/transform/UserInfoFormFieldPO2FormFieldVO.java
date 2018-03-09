package com.cdut.transform;

import com.cdut.entity.menu.UserInfoFormPO;
import com.cdut.vo.FormFieldVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/10/31.
 */
public class UserInfoFormFieldPO2FormFieldVO implements Function<UserInfoFormPO, FormFieldVO> {
    @Nullable
    @Override
    public FormFieldVO apply(@Nullable UserInfoFormPO po) {

        if (po != null) {
            FormFieldVO vo = new FormFieldVO();
            vo.setId(po.getId());
            vo.setName(po.getName());
            vo.setType(po.getType());
            vo.setLabel(po.getLabel());
            vo.setTips(po.getTips());
            vo.setRequired(po.getRequired());
            vo.setReadonly(po.getReadOnly());
            vo.setIndexNum(po.getIndexNum());
            vo.setInputParams(po.getInputParams());
            vo.setDefaultValue(po.getDefaultValue());
            return vo;
        }
        return null;
    }
}
