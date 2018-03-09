package com.cdut.service.page;

import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.dao.mysql.page.SideDateTimeRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.page.SideTimePO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.SideTimePO2SideTimeRespVO;
import com.cdut.vo.SideTimeRespVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by king on 2017/10/29.
 */
@Service
public class SideDateTimeServiceImpl extends BaseService implements SideDateTimeService {

    @Autowired
    private SideDateTimeRepository sideDateTimeRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public JsonResult saveDateTime(SideTimeRespVO vo) {
        SideTimePO po = new SideTimePO();
        PageInfoPO pageInfoPO = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        po.setIndexNum(vo.getIndexNum());
        po.setEvent(vo.getEvent());
        po.setColor(vo.getColor());
        po.setTime(vo.getTime());
        po.setPageInfoPO(pageInfoPO);
        SideTimePO returnPO = sideDateTimeRepository.save(po);
        SideTimeRespVO result = new SideTimeRespVO();
        result.setId(returnPO.getId());
        result.setColor(returnPO.getColor());
        result.setEvent(returnPO.getEvent());
        result.setIndexNum(returnPO.getIndexNum());
        result.setTime(returnPO.getTime());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult deleteDateTimeById(Long id) {
        sideDateTimeRepository.delete(id);
        return new JsonResult("deleted", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult updateDateTime(SideTimeRespVO vo) {
        SideTimePO sideTimePO  = sideDateTimeRepository.findOne(vo.getId());
        sideTimePO.setTime(vo.getTime());
        sideTimePO.setColor(vo.getColor());
        sideTimePO.setEvent(vo.getEvent());
        sideTimePO.setIndexNum(vo.getIndexNum());
        sideDateTimeRepository.save(sideTimePO);
        return new JsonResult(vo, "Ok", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getAll() {
        List<SideTimePO> sideTimePOS = sideDateTimeRepository.findAll();
        if (CollectionUtils.isEmpty(sideTimePOS)) {
            return new JsonResult(Lists.newArrayList(), "dateTime is null", ResultStatus.SUCCESS);
        }
        List<SideTimeRespVO> sideTimeRespVOS = Lists.transform(sideTimePOS, new SideTimePO2SideTimeRespVO());
        return new JsonResult(sideTimeRespVOS, "OK", ResultStatus.SUCCESS);
    }
}
