package com.cdut.dao.mysql.menu;

import com.cdut.dao.common.repository.CommonJpaRepositoryBean;
import com.cdut.entity.menu.MenuPO;
import com.cdut.vo.MenuOneLevelRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
public class MenuRepositoryImpl extends CommonJpaRepositoryBean<MenuPO, Integer> implements MenuDao {

    @Autowired
    public MenuRepositoryImpl(EntityManager em) {
        super(MenuPO.class, em);
    }

    @Override
    public List<MenuOneLevelRespVO> findOneLevelMenu() {

        String sql = " SELECT \n" +
                " menu_side_bar.id AS id, \n" +
                " menu_side_bar.name AS name, \n" +
                " menu_side_bar.index_num AS indexNum \n" +
                " FROM menu_side_bar\n" +
                " WHERE \n" +
                " menu_side_bar.parent_id IS NULL \n";
        Query query = getEntityManager().createNativeQuery(sql, "oneMenuLevel");
        return query.getResultList();
    }
}
