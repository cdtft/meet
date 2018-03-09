package com.cdut.dao.mysql.user;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.user.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/10/30.
 */
public interface RoleRepository extends CommonJpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Role findByName(String name);
}
