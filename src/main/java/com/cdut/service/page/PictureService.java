package com.cdut.service.page;

import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PicturePO;
import com.cdut.vo.PictureVO;

import java.util.List;

/**
 * Created by king on 2017/10/28.
 */
public interface PictureService {

    JsonResult addPicture(PictureVO vo);

    JsonResult updatePicture(PictureVO vo);

    JsonResult deletePicture(Long id);

    JsonResult findAll();
}
