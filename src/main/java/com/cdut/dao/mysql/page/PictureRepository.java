package com.cdut.dao.mysql.page;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.PicturePO;
import com.cdut.enums.PictureTypeEnum;
import com.cdut.vo.PictureVO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by king on 2017/10/28.
 */
@Repository
public interface PictureRepository extends CommonJpaRepository<PicturePO, Long>, JpaSpecificationExecutor<PicturePO> {

    PicturePO findByPictureTypeEnum(PictureTypeEnum typeEnum);

}
