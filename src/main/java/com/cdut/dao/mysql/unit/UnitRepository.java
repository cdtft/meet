package com.cdut.dao.mysql.unit;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.UnitPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/10/28.
 */
public interface UnitRepository extends CommonJpaRepository<UnitPO, Long>, JpaSpecificationExecutor<UnitPO> {
}
