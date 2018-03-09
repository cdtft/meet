package com.cdut.service.page;

import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.dao.mysql.page.PictureRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.page.PicturePO;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.PictureTypeEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.PicturePO2PictureVO;
import com.cdut.vo.PictureVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
@Service
public class PictureServiceImpl extends BaseService implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    @Transactional
    public JsonResult addPicture(PictureVO vo) {
        PageInfoPO pageInfoPO = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        if (vo.getPictureTypeEnum().equals(PictureTypeEnum.QRCODE) || vo.getPictureTypeEnum().equals(PictureTypeEnum.LOGO) || vo.getPictureTypeEnum().equals(PictureTypeEnum.BACKGROUND)) {
            PicturePO po = pictureRepository.findByPictureTypeEnum(vo.getPictureTypeEnum());
            if (po != null) {
                po.setUrl(vo.getUrl());
                po.setTitle(vo.getTitle());
                po.setIndexNum(vo.getIndexNum());
                PicturePO returnPO = pictureRepository.save(po);
                PictureVO resultVO = new PictureVO();
                resultVO.setName(returnPO.getName());
                resultVO.setPictureTypeEnum(returnPO.getPictureTypeEnum());
                resultVO.setUrl(returnPO.getUrl());
                resultVO.setTitle(returnPO.getTitle());
                resultVO.setId(returnPO.getId());
                logger.info("更新图片");
                return new JsonResult(resultVO, "OK", ResultStatus.SUCCESS);
            }
            po = new PicturePO();
            po.setUrl(vo.getUrl());
            po.setTitle(vo.getTitle());
            po.setPictureTypeEnum(vo.getPictureTypeEnum());
            po.setPageInfoPO(pageInfoPO);
            po.setIndexNum(vo.getIndexNum());
            po.setName(vo.getName());
            PicturePO returnPO = pictureRepository.save(po);
            PictureVO resultVO = new PictureVO();
            resultVO.setName(returnPO.getName());
            resultVO.setPictureTypeEnum(returnPO.getPictureTypeEnum());
            resultVO.setUrl(returnPO.getUrl());
            resultVO.setTitle(returnPO.getTitle());
            resultVO.setId(returnPO.getId());
            logger.info("添加图片");
            return new JsonResult(resultVO, "添加成功", ResultStatus.SUCCESS);
        }
        PicturePO po = new PicturePO();
        po.setUrl(vo.getUrl());
        po.setTitle(vo.getTitle());
        po.setPictureTypeEnum(vo.getPictureTypeEnum());
        po.setPageInfoPO(pageInfoPO);
        po.setForwardUrl(vo.getForwardUrl());
        po.setIndexNum(vo.getIndexNum());
        po.setName(vo.getName());
        PicturePO returnPO = pictureRepository.save(po);
        PictureVO resultVO = new PictureVO();
        resultVO.setName(returnPO.getName());
        resultVO.setPictureTypeEnum(returnPO.getPictureTypeEnum());
        resultVO.setUrl(returnPO.getUrl());
        resultVO.setTitle(returnPO.getTitle());
        resultVO.setId(returnPO.getId());
        resultVO.setForwardUrl(returnPO.getForwardUrl());
        resultVO.setIndexNum(returnPO.getIndexNum());
        logger.info("添加图片");
        return new JsonResult(resultVO, "添加成功", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult updatePicture(PictureVO vo) {
        PicturePO picturePO = pictureRepository.findOne(vo.getId());
        picturePO.setIndexNum(vo.getIndexNum());
        picturePO.setName(vo.getName());
        picturePO.setTitle(vo.getTitle());
        picturePO.setForwardUrl(vo.getForwardUrl());
        picturePO.setUrl(vo.getUrl());
        PicturePO returnPO = pictureRepository.save(picturePO);
        PictureVO result = new PictureVO();
        result.setId(returnPO.getId());
        result.setIndexNum(returnPO.getIndexNum());
        result.setTitle(returnPO.getTitle());
        result.setForwardUrl(returnPO.getForwardUrl());
        result.setUrl(returnPO.getUrl());
        result.setName(returnPO.getName());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult deletePicture(Long id) {
        pictureRepository.delete(id);
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findAll() {
        List<PicturePO> picturePOList = pictureRepository.findAll();
        List<PictureVO> pictureVOS = Lists.transform(picturePOList, new PicturePO2PictureVO());
        return new JsonResult(pictureVOS, "ALL picture", ResultStatus.SUCCESS);
    }
}
