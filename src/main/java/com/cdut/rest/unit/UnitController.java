package com.cdut.rest.unit;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.service.unit.UnitItemService;
import com.cdut.service.unit.UnitService;
import com.cdut.vo.UnitItemReqVO;
import com.cdut.vo.UnitReqVO;
import com.cdut.vo.UnitRespVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
@RestController
public class UnitController extends BaseRestController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitItemService unitItemService;

    /**
     * 添加单位类型
     * @param unitType
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unit", method = RequestMethod.POST)
    public JsonResult saveUnit(@RequestBody UnitReqVO unitType) {
        logger.info("添加的unitType {[]}", unitType);
        return unitService.save(unitType);
    }

    /**
     * 根据id删除单位类型
     * @param id
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unit/{unitId}", method = RequestMethod.DELETE)
    public JsonResult deleteUnitById(@PathVariable("unitId") Long id) {
        if (id == null) {
            logger.error("删除的unit id 为空");
            return new JsonResult("id为空", ResultStatus.INVALID_REQUEST);
        }
        logger.info("删除unit的id [{}]", id);
        return unitService.deleteUnitById(id);
    }

    /**
     * 修改单位类型
     * @param unitRespVO
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unit", method = RequestMethod.PUT)
    public JsonResult updateUnit(@RequestBody UnitRespVO unitRespVO) {
        return unitService.updateUnitName(unitRespVO);
    }


    /**
     * 添加unitItem
     * @param unitId
     * @param vo
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unit/{unitId}/unitItem", method = RequestMethod.POST)
    public JsonResult addUnitItem(@PathVariable("unitId") Long unitId, @RequestBody UnitItemReqVO vo ) {
        if (vo == null) {
            logger.info("unitItem is null");
            return new JsonResult("unitItemList为空", ResultStatus.INVALID_REQUEST);
        }
        return unitItemService.saveUnitItemByUnitId(unitId, vo);
    }

    /**
     * 修改UnitItem
     * @param vo
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unitItem", method = RequestMethod.PUT)
    public JsonResult updateUnitItem(@RequestBody UnitItemReqVO vo) {
        return unitItemService.updateUnitItem(vo);
    }

    /**
     * 删除unitItem
     * @param id
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/unitItem/{unitItemId}", method = RequestMethod.DELETE)
    public JsonResult deleteUnitItem(@PathVariable("unitItemId") Long id) {
        if (id == null) {
            logger.error("unitItemId is null");
            return new JsonResult("删除的id为空", ResultStatus.INVALID_REQUEST);
        }
        return unitItemService.deleteUnitItemById(id);
    }

    /**
     * 更具unitId获取该单位类型下的所有unitItem
     * @param unitId
     * @return
     */
    @RequestMapping(value = "v1/api/unit/{unitId}/unitItem", method = RequestMethod.GET)
    public JsonResult getUnitItem(@PathVariable("unitId") Long unitId) {
        return unitService.getUnitItemById(unitId);
    }

    /**
     * 查询所用的unit
     * @return
     */
    @RequestMapping(value = "v1/api/allUnit", method = RequestMethod.GET)
    public JsonResult getAllUnit() {
        return unitService.getAllUnit();
    }

}
