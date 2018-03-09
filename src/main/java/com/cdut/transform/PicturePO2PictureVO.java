package com.cdut.transform;

import com.cdut.entity.page.PicturePO;
import com.cdut.vo.PictureVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/10/29.
 */
public class PicturePO2PictureVO implements Function<PicturePO,PictureVO> {
    @Nullable
    @Override
    public PictureVO apply(@Nullable PicturePO po) {
        if (po != null) {
            PictureVO vo = new PictureVO();
            vo.setName(po.getName());
            vo.setUrl(po.getUrl());
            vo.setTitle(po.getTitle());
            vo.setIndexNum(po.getIndexNum());
            vo.setForwardUrl(vo.getForwardUrl());
            vo.setId(po.getId());
            vo.setPictureTypeEnum(po.getPictureTypeEnum());
            return vo;
        }
        return null;
    }
}
