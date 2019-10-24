package com.ait.auth.domain.service;

import com.ait.auth.domain.dto.AddUserDto;
import com.ait.auth.domain.dto.AuthorityDto;
import com.ait.auth.domain.dto.ChangeUserDto;
import com.ait.auth.domain.dto.UserDto;
import com.ait.auth.domain.entitiy.AuthoritiesEntity;
import com.ait.auth.domain.entitiy.UsersEntity;
import com.ait.auth.domain.exception.AuthorityNotFoundException;
import com.ait.auth.domain.exception.InvalidCredentialsException;
import com.ait.auth.domain.exception.UserExistsException;
import com.ait.auth.domain.mapper.UserMapper;
import com.ait.auth.domain.repository.AuthoritiesRepository;
import com.ait.auth.domain.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserMapper userMapper;

    private static final String ROLE_GUEST_AUTHORITY = "ROLE_GUEST";

    @Autowired
    public UsersService(UsersRepository usersRepository,
                        AuthoritiesRepository authoritiesRepository,
                        UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.userMapper = userMapper;
    }

    @Secured("ROLE_USER")
    public List<UserDto> findAllUsers() {
        List<UsersEntity> usersEntities = usersRepository.findAll();
        return userMapper.mapAsList(usersEntities, UserDto.class);
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
                .password(getEncodedPassword(addUserDto.getPassword()))
                .enabled(1)
                .authorities(authoritiesEntities)
                .build();

        user = usersRepository.save(user);
        return userMapper.map(user, UserDto.class);
    }

    @PreAuthorize("(#changeUserDto.getUsername() == authentication.principal and hasRole('ROLE_GUEST')) or hasRole('ROLE_USER')")
    public UserDto changeUser(ChangeUserDto changeUserDto) {
        UsersEntity user = usersRepository.findByUsername(changeUserDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(changeUserDto.getUsername()));

        if (!(new BCryptPasswordEncoder().matches(changeUserDto.getOldPassword(), user.getPassword().substring(8)))) {
            throw new InvalidCredentialsException();
        }
        user.setPassword(changeUserDto.getNewPassword() != null ?
                getEncodedPassword(changeUserDto.getNewPassword()) :
                user.getPassword());

        user.setEnabled(changeUserDto.getEnabled() != null ? changeUserDto.getEnabled() : user.getEnabled());

        user = usersRepository.save(user);

        return userMapper.map(user, UserDto.class);
    }

    private String getEncodedPassword(String password) {
        return String.format("%s%s", "{bcrypt}", new BCryptPasswordEncoder().encode(password));
    }
}
