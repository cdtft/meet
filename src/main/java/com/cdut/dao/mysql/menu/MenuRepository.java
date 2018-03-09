package com.cdut.dao.mysql.menu;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.menu.MenuPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
@Repository
public interface MenuRepository extends CommonJpaRepository<MenuPO, Long>, JpaSpecificationExecutor<MenuPO>, MenuDao {

    //List<MenuPO> findByParentMenuPOIsNull();

    MenuPO findByName(String name);

    List<MenuPO> findByIdIn(Iterable<Long> ids);
}
