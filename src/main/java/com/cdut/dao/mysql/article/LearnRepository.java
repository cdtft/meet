package com.cdut.dao.mysql.article;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.article.LearnPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/10/31.
 */
public interface LearnRepository extends CommonJpaRepository<LearnPO, Long>, JpaSpecificationExecutor<LearnPO> {

}
