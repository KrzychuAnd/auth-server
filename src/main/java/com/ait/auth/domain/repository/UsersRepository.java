package com.ait.auth.domain.repository;

import com.ait.auth.domain.entitiy.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    List<UsersEntity> findAll();
    Optional<UsersEntity> findByUsername(String username);
}
