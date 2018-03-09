package com.cdut.service.unit;

import com.cdut.dao.mysql.unit.UnitItemRepository;
import com.cdut.dao.mysql.unit.UnitRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.UnitItemPO;
import com.cdut.entity.page.UnitPO;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.UnitItemReqVO2UnitItemPO;
import com.cdut.vo.UnitItemReqVO;
import com.cdut.vo.UnitItemRespVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
@Service
public class UnitItemServiceImpl extends BaseService implements UnitItemService {

    @Autowired
    private UnitItemRepository unitItemRepository;

    @Autowired
    private UnitRepository unitRepository;


    @Override
    public JsonResult saveUnitItemByUnitId(Long id, UnitItemReqVO vo) {
        if (id == null || vo == null) {
            logger.info("unit id is null or unitItem is null");
            return new JsonResult("unit id is null or unitItem is null", ResultStatus.INVALID_REQUEST);
        }
        UnitPO unitPO = unitRepository.findOne(id);
        if (unitPO == null) {
            logger.info("not found unit id is [{}]", id);
            return new JsonResult("为找到对应id的unit", ResultStatus.NOT_FOUND);
        }
        //List<UnitItemPO> unitItemPOS = Lists.transform(unitItemReqVOList, new UnitItemReqVO2UnitItemPO(idService));
        UnitItemPO unitItemPO = new UnitItemPO();
        unitItemPO.setUnitPO(unitPO);
        unitItemPO.setName(vo.getName());
        unitItemPO.setIndexNum(vo.getIndexNum());
        UnitItemPO result = unitItemRepository.save(unitItemPO);
        UnitItemRespVO returnVO = new UnitItemRespVO();
        returnVO.setIndexNum(result.getIndexNum());
        returnVO.setId(result.getId());
        returnVO.setName(result.getName());
        logger.info("save all unit items");
        return new JsonResult(returnVO, "unitItem 保存成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult deleteUnitItemById(Long id) {
        UnitItemPO unitItemPO = unitItemRepository.findOne(id);
        if (unitItemPO == null) {
            logger.error("没有对应的unitItem记录[{}]", id);
            return new JsonResult("没有对用的unitItem记录", ResultStatus.INVALID_REQUEST);
        }
        unitItemRepository.delete(id);
        logger.info("删除成功");
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult updateUnitItem(UnitItemReqVO vo) {
        Long itemId = vo.getId();
        UnitItemPO po = unitItemRepository.findOne(itemId);
        po.setName(vo.getName());
        po.setIndexNum(vo.getIndexNum());
        UnitItemPO returnPO = unitItemRepository.save(po);
        UnitItemRespVO result = new UnitItemRespVO();
        result.setIndexNum(returnPO.getIndexNum());
        result.setName(returnPO.getName());
        result.setId(returnPO.getId());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }
}
