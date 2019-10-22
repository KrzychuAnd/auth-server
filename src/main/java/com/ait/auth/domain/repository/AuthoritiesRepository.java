package com.ait.auth.domain.repository;

import com.ait.auth.domain.entitiy.AuthoritiesEntity;
import com.ait.auth.domain.entitiy.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Long> {
    Optional<AuthoritiesEntity> findByAuthority(String authority);
}
