package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select count(user.userEmail) from UserEntity user where user.userEmail=:email")
    Long countEmails(@Param("email") String email);

    @Query("select user from UserEntity user where user.userEmail=:email")
    Optional<UserEntity> find(@Param("email") String email);
}
