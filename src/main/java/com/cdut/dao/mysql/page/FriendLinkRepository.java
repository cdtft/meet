package com.cdut.dao.mysql.page;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.page.FriendLinkPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/11/6.
 */
public interface FriendLinkRepository extends CommonJpaRepository<FriendLinkPO, Long>, JpaSpecificationExecutor<FriendLinkPO> {
}
