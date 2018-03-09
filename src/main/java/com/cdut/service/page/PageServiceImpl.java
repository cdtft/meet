package com.cdut.service.page;

import com.cdut.dao.mysql.article.LearnRepository;
import com.cdut.dao.mysql.article.NewsRepository;
import com.cdut.dao.mysql.article.NoticeRepository;
import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.FriendLinkPO;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.page.PicturePO;
import com.cdut.entity.page.SideTimePO;
import com.cdut.entity.page.UnitItemPO;
import com.cdut.entity.page.UnitPO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.PictureTypeEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.FriendLinkPO2FriendLinkVO;
import com.cdut.transform.SideTimePO2SideTimeRespVO;
import com.cdut.transform.UnitItemPO2UnitRespVO;
import com.cdut.vo.CarouselPictureVO;
import com.cdut.vo.FriendLinkVO;
import com.cdut.vo.PageInfoRespVO;
import com.cdut.vo.SideTimeRespVO;
import com.cdut.vo.UnitRespVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by king on 2017/10/25.
 */
@Service
public class PageServiceImpl extends BaseService implements PageService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private LearnRepository learnRepository;
    @Override
    public JsonResult findOnePageInfo() throws Exception {

        PageInfoPO po = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        if (po != null) {
            PageInfoRespVO vo = new PageInfoRespVO();
            //加载首页图片
            List<PicturePO> picturePOList = po.getPicturePOS();
            if (CollectionUtils.isNotEmpty(picturePOList)) {
                List<CarouselPictureVO> carouselList = Lists.newArrayList();
                for (PicturePO picturePO : picturePOList) {
                    if (picturePO.getPictureTypeEnum().equals(PictureTypeEnum.QRCODE)) {
                        vo.setQrCodeUrl(picturePO.getUrl());
                        vo.setQrTitle(picturePO.getTitle());
                    }
                    if (picturePO.getPictureTypeEnum().equals(PictureTypeEnum.LOGO)) {
                        vo.setLogoUrl(picturePO.getUrl());
                        vo.setLogoTitle(picturePO.getTitle());
                    }
                    if (picturePO.getPictureTypeEnum().equals(PictureTypeEnum.CAROUSEL)) {
                        CarouselPictureVO carouselPictureVO = new CarouselPictureVO();
                        carouselPictureVO.setUrl(picturePO.getUrl());
                        carouselPictureVO.setForwardUrl(picturePO.getForwardUrl());
                        carouselList.add(carouselPictureVO);
                    }
                    if (picturePO.getPictureTypeEnum().equals(PictureTypeEnum.BACKGROUND)) {
                        vo.setBgUrl(picturePO.getUrl());
                    }
                }
                vo.setCarouselList(carouselList);
            }
            //首页标题
            vo.setTitleName(po.getTitle());
            vo.setSubTitle(po.getSubTitle());
            //首页侧边栏日期
            List<SideTimePO> sideTimePOS = po.getSideTimePOS();
            List<SideTimeRespVO> sideTimeRespVOS = Lists.transform(sideTimePOS, new SideTimePO2SideTimeRespVO());
            vo.setSideTimeRespVOList(sideTimeRespVOS);
            //加载单位
            List<UnitPO> unitPOS = po.getUnitPOS();
            Map<String, List<UnitItemPO>> unitItemPoMap = unitPOS.stream().collect(Collectors.toMap(UnitPO::getName, UnitPO::getUnitItemPOS));
            List<UnitRespVO> unitRespVOS = Lists.newArrayList();
            for (UnitPO unitPO : unitPOS) {
                UnitRespVO unitRespVO = new UnitRespVO();
                unitRespVO.setName(unitPO.getName());
                List<UnitItemPO> unitItemPOS = unitItemPoMap.get(unitPO.getName());
                unitRespVO.setUnitItemRespVOList(Lists.transform(unitItemPOS, new UnitItemPO2UnitRespVO()));
                unitRespVO.setIndexNum(unitPO.getIndexNum());
                unitRespVOS.add(unitRespVO);
            }
            vo.setUnitRespVOList(unitRespVOS);
            //加载友情链接
            List<FriendLinkPO> friendLinkPOS = po.getFriendLinkPOS();
            List<FriendLinkVO> friendLinkVOList = Lists.transform(friendLinkPOS, new FriendLinkPO2FriendLinkVO());
            vo.setFriendLinkVOList(friendLinkVOList);
            //是否开放注册
            vo.setEnableRegister(po.getEnableRegister());
            vo.setLocation(po.getLocation());
            vo.setTime(po.getTime());
            return new JsonResult(vo, "首页信息", ResultStatus.SUCCESS);
        }
        logger.info("页面信息为空");
        return new JsonResult("页面信息为空", ResultStatus.NOT_FOUND);
    }

    @Override
    public JsonResult updatePageTitle(PageInfoRespVO vo) throws Exception {
        PageInfoPO po = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        if (StringUtils.isNotBlank(vo.getLocation())) {
            logger.info("更新首页信息 开会地点 [{}] --> [{}]", po.getLocation(), vo.getLocation());
            po.setLocation(vo.getLocation());
        }
        if(StringUtils.isNotBlank(vo.getTime())) {
            logger.info("更新首页信息 会议时间 [{}] --> [{}]", po.getTime(), vo.getTime());
            po.setTime(vo.getTime());
        }
        if(StringUtils.isNotBlank(vo.getTitleName())) {
            logger.info("更新首页信息 首页标题 [{}] --> [{}]", po.getTitle(), vo.getTitleName());
            po.setTitle(vo.getTitleName());
        }
        if(StringUtils.isNotBlank(vo.getSubTitle())) {
            logger.info("更新首页信息 下标题 [{}] --> [{}]", po.getSubTitle(), vo.getSubTitle());
            po.setSubTitle(vo.getSubTitle());
        }
        if (vo.getEnableRegister() != null) {
            logger.info("更新首页信息 能否注册 [{}] --> [{}]", po.getEnableRegister(), vo.getEnableRegister());
            po.setEnableRegister(vo.getEnableRegister());
        }
        pageRepository.save(po);
        return findOnePageInfo();
    }
}
