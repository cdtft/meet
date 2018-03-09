package com.cdut.dao.mysql.user;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by king on 2017/10/13.
 */
@Repository
public interface UserRepository extends CommonJpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    User findById(Long id);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePasswordById(@Param("id") Long id, @Param("password") String password);

    List<User> findByIdIn(Iterable<Long> ids);

    User findByEmail(String email);
}
