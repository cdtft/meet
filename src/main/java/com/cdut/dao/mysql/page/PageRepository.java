package com.cdut.dao.mysql.page;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.enums.CommonStatusEnum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2017/10/25.
 */
@Repository
public interface PageRepository extends CommonJpaRepository<PageInfoPO, Long>, JpaSpecificationExecutor<PageInfoPO> {

    PageInfoPO findByCommonStatusEnum(CommonStatusEnum status);
}
