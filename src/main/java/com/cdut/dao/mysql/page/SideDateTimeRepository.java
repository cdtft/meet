package com.cdut.dao.mysql.page;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.SideTimePO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by king on 2017/10/29.
 */
@Repository
public interface SideDateTimeRepository extends CommonJpaRepository<SideTimePO, Long>, JpaSpecificationExecutor<SideTimePO> {

}
