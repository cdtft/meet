package com.cdut.service.unit;

import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.dao.mysql.unit.UnitRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.page.UnitItemPO;
import com.cdut.entity.page.UnitPO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.UnitItemPO2UnitRespVO;
import com.cdut.transform.UnitPo2UnitRespVO;
import com.cdut.vo.UnitItemRespVO;
import com.cdut.vo.UnitReqVO;
import com.cdut.vo.UnitRespVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
@Service
public class UnitServiceImpl extends BaseService implements  UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    @Transactional
    public JsonResult save(UnitReqVO unitReqVO) {
        if (unitReqVO == null) {
            return new JsonResult("单位类型为空", ResultStatus.INVALID_REQUEST);
        }
        PageInfoPO pageInfoPO = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        UnitPO po = new UnitPO();
        po.setPageInfoPO(pageInfoPO);
        po.setName(unitReqVO.getName());
        po.setIndexNum(unitReqVO.getIndexNum());
        UnitPO returnPo = unitRepository.save(po);
        UnitRespVO vo = new UnitRespVO();
        vo.setIndexNum(returnPo.getIndexNum());
        vo.setName(returnPo.getName());
        vo.setId(returnPo.getId());
        logger.info("单位类型保存成功");
        return new JsonResult(vo,"OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult deleteUnitById(Long id) {

        UnitPO po = unitRepository.findOne(id);
        if (po == null) {
            logger.error("unit id [{}] 没用对应的数据记录");
            return new JsonResult("没有找到对应的unit id", ResultStatus.INVALID_REQUEST);
        }
        unitRepository.delete(id);
        logger.info("unit [{}] 删除成功");
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult updateUnitName(UnitRespVO unitRespVO) {

        if (StringUtils.isBlank(unitRespVO.getName())) {
            return new JsonResult("更新的name为空", ResultStatus.INVALID_REQUEST);
        }
        UnitPO po = unitRepository.findOne(unitRespVO.getId());
        if (po == null) {
            return new JsonResult("未找到该单位类型", ResultStatus.NOT_FOUND);
        }
        po.setName(unitRespVO.getName());
        po.setIndexNum(unitRespVO.getIndexNum());
        UnitPO returnPO = unitRepository.save(po);
        UnitRespVO result = new UnitRespVO();
        result.setIndexNum(returnPO.getIndexNum());
        result.setName(returnPO.getName());
        logger.info("unit更新成功");
        return new JsonResult(result, "unit更新成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getAllUnit() {
        List<UnitPO> unitPOS =  unitRepository.findAll();
        List<UnitRespVO> unitRespVOList = Lists.transform(unitPOS, new UnitPo2UnitRespVO());
        return new JsonResult(unitRespVOList, "All unit", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getUnitItemById(Long id) {
        UnitPO po = unitRepository.getOne(id);
        List<UnitItemPO> unitItemPOS = po.getUnitItemPOS();
        List<UnitItemRespVO> result = Lists.transform(unitItemPOS, new UnitItemPO2UnitRespVO());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }
}
