package com.cdut.dao.mysql.menu;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.menu.UserInfoFormPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by king on 2017/10/31.
 */
@Repository
public interface UserInfoFormRepository extends CommonJpaRepository<UserInfoFormPO, Long>, JpaSpecificationExecutor<UserInfoFormPO> {
}
