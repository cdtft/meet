package com.cdut.dao.mysql.unit;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.UnitItemPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/10/28.
 */
public interface UnitItemRepository extends CommonJpaRepository<UnitItemPO, Long>, JpaSpecificationExecutor<UnitItemPO> {

}
