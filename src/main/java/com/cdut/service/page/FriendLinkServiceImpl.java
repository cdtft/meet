package com.cdut.service.page;

import com.cdut.dao.mysql.page.FriendLinkRepository;
import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.FriendLinkPO;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.FriendLinkPO2FriendLinkVO;
import com.cdut.vo.FriendLinkVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by king on 2017/11/6.
 */
@Service
public class FriendLinkServiceImpl extends BaseService implements FriendLinkService {

    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public JsonResult addFriendLink(FriendLinkVO vo) {
        FriendLinkPO po = new FriendLinkPO();
        PageInfoPO pageInfoPO = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        po.setText(vo.getText().trim());
        po.setUrl(vo.getUrl().trim());
        po.setIndexNum(vo.getIndexNum());
        po.setPageInfoPO(pageInfoPO);
        FriendLinkPO returnPO = friendLinkRepository.save(po);
        logger.info("[{}] friendLink保存成功", returnPO);
        FriendLinkVO result = new FriendLinkVO();
        result.setId(returnPO.getId());
        result.setText(returnPO.getText());
        result.setIndexNum(returnPO.getIndexNum());
        result.setUrl(returnPO.getUrl());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult updateFriendLink(FriendLinkVO vo) {
        if (vo.getId() == null) {
            logger.info("[{}] id为空", vo);
            return new JsonResult("id为空", ResultStatus.INVALID_REQUEST);
        }
        FriendLinkPO po = friendLinkRepository.findOne(vo.getId());
        if (po == null) {
            logger.info("未找到对应的friendLink");
            return new JsonResult("未找到对应的friendLink", ResultStatus.NOT_FOUND);
        }
        po.setUrl(vo.getUrl());
        po.setIndexNum(vo.getIndexNum());
        po.setText(vo.getText());
        FriendLinkPO returnPO = friendLinkRepository.save(po);
        logger.info("[{}] friendLink更新成功", returnPO);
        FriendLinkVO result = new FriendLinkVO();
        result.setId(returnPO.getId());
        result.setText(returnPO.getText());
        result.setIndexNum(returnPO.getIndexNum());
        result.setUrl(returnPO.getUrl());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult deleteFriendLink(Long id) {
        if (id == null) {
            logger.info("id为空");
            return new JsonResult("id为空", ResultStatus.INVALID_REQUEST);
        }
        friendLinkRepository.delete(id);
        logger.info("友情链接删除成功 [{}]", id);
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findAll() {
        List<FriendLinkPO> friendLinkPOS = friendLinkRepository.findAll();
        if (CollectionUtils.isEmpty(friendLinkPOS)) {
            return new JsonResult("友情链接为空", ResultStatus.SUCCESS);
        }
        List<FriendLinkVO> friendLinkVOList = Lists.transform(friendLinkPOS, new FriendLinkPO2FriendLinkVO());
        return new JsonResult(friendLinkVOList, "OK", ResultStatus.SUCCESS);
    }
}
