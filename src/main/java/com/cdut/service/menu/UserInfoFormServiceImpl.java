package com.cdut.service.menu;

import com.cdut.dao.mysql.menu.UserInfoFormRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.menu.UserInfoFormPO;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.UserInfoFormFieldPO2FormFieldVO;
import com.cdut.vo.FormFieldVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2017/10/31.
 */
@Service
public class UserInfoFormServiceImpl extends BaseService implements UserInfoFormService {

    @Autowired
    private UserInfoFormRepository userInfoFormRepository;

    @Override
    public JsonResult findAllFormField() {
        List<UserInfoFormPO> userInfoFormPOS = userInfoFormRepository.findAll();
        if (CollectionUtils.isEmpty(userInfoFormPOS)) {
            logger.info("表单信息为空");
            return new JsonResult("表单信息为空", ResultStatus.SUCCESS);
        }
        List<FormFieldVO> formFieldVOList = Lists.transform(userInfoFormPOS, new UserInfoFormFieldPO2FormFieldVO());
        return new JsonResult(formFieldVOList, "OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult addFormField(FormFieldVO vo) {
        UserInfoFormPO po = new UserInfoFormPO();
        po.setName(vo.getName());
        po.setType(vo.getType());
        po.setLabel(vo.getLabel());
        po.setTips(vo.getTips());
        po.setRequired(vo.getRequired());
        po.setReadOnly(vo.getReadonly());
        po.setIndexNum(vo.getIndexNum());
        po.setInputParams(vo.getInputParams());
        po.setDefaultValue(vo.getDefaultValue());
        UserInfoFormPO returnPO = userInfoFormRepository.save(po);
        FormFieldVO result = new UserInfoFormFieldPO2FormFieldVO().apply(returnPO);
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult deleteFormFieldById(Long id) {
        userInfoFormRepository.delete(id);
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult updateFormFieldById(FormFieldVO vo) {
        UserInfoFormPO po = userInfoFormRepository.findOne(vo.getId());
        if (po == null) {
            return new JsonResult("未找到对应id from field", ResultStatus.SUCCESS);
        }
        po.setName(vo.getName());
        po.setType(vo.getType());
        po.setLabel(vo.getLabel());
        po.setTips(vo.getTips());
        po.setRequired(vo.getRequired());
        po.setReadOnly(vo.getReadonly());
        po.setIndexNum(vo.getIndexNum());
        po.setInputParams(vo.getInputParams());
        po.setDefaultValue(vo.getDefaultValue());
        UserInfoFormPO returnPO = userInfoFormRepository.save(po);
        FormFieldVO result = new UserInfoFormFieldPO2FormFieldVO().apply(returnPO);
        return new JsonResult(result, "更新成功", ResultStatus.SUCCESS);
    }
}
