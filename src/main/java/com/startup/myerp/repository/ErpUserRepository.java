package com.startup.myerp.repository;

import com.startup.myerp.entity.ErpUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErpUserRepository extends JpaRepository<ErpUser, Long> {
    Optional<ErpUser> findByUserName(String username);
}
