package com.ait.auth.domain.service;

import com.ait.auth.domain.dto.AddUserDto;
import com.ait.auth.domain.dto.AuthorityDto;
import com.ait.auth.domain.dto.UserDto;
import com.ait.auth.domain.entitiy.AuthoritiesEntity;
import com.ait.auth.domain.entitiy.UsersEntity;
import com.ait.auth.domain.exception.AuthorityNotFoundException;
import com.ait.auth.domain.exception.UserExistsException;
import com.ait.auth.domain.repository.AuthoritiesRepository;
import com.ait.auth.domain.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private AuthoritiesRepository authoritiesRepository;

    private static final String ROLE_GUEST_AUTHORITY = "ROLE_GUEST";

    @Autowired
    public UsersService(UsersRepository usersRepository,
                        AuthoritiesRepository authoritiesRepository) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Secured("ROLE_USER")
    public List<UserDto> findAllUsers() {
        List<UsersEntity> usersEntities = usersRepository.findAll();
        return usersEntities.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .enabled(user.getEnabled())
                        .authorities(user.getAuthorities().stream()
                                .map(a -> AuthorityDto.builder()
                                        .id(a.getId())
                                        .authority(a.getAuthority())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<UsersEntity> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UserDto addUser(AddUserDto addUserDto) {
        usersRepository.findByUsername(addUserDto.getUsername())
                .ifPresent(user -> {throw new UserExistsException(user.getUsername());});

        AuthoritiesEntity authority = authoritiesRepository.findByAuthority(ROLE_GUEST_AUTHORITY)
                .orElseThrow(() -> new AuthorityNotFoundException(ROLE_GUEST_AUTHORITY));

        Set<AuthoritiesEntity> authoritiesEntities = Stream.of(authority).collect(Collectors.toSet());

        UsersEntity user = UsersEntity.builder()
                .username(addUserDto.getUsername())
                .password(String.format("%s%s", "{bcrypt}", new BCryptPasswordEncoder().encode(addUserDto.getPassword())))
                .enabled(1)
                .authorities(authoritiesEntities)
                .build();

        user = usersRepository.save(user);

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.getEnabled())
                .authorities(authoritiesEntities.stream()
                        .map(a -> AuthorityDto.builder()
                                .id(a.getId())
                                .authority(a.getAuthority())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
