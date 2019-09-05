package com.ait.auth.domain.service;

import com.ait.auth.domain.entitiy.UsersEntity;
import com.ait.auth.domain.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<UsersEntity> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
