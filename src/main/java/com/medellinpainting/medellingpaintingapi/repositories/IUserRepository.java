package com.medellinpainting.medellingpaintingapi.repositories;

import com.medellinpainting.medellingpaintingapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(String username);

    @Query("select count(u.username) from User u where u.username = :username")
    int buscarUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "insert into roles (rol, user_id) values (:rol, :user_id)", nativeQuery = true)
    void insRol(@Param("rol") String rol, @Param("user_id") Long userId);
}
