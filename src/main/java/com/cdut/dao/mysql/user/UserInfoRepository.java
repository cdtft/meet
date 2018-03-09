package com.cdut.dao.mysql.user;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2017/10/31.
 */
@Repository
public interface UserInfoRepository extends CommonJpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    UserInfo findByUserId(Long id);

    List<UserInfo> findByUserIdIn(Iterable<Long> ids);
}
