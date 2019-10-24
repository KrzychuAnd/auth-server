package com.ait.auth.domain.mapper;

import com.ait.auth.domain.dto.AuthorityDto;
import com.ait.auth.domain.dto.UserDto;
import com.ait.auth.domain.entitiy.AuthoritiesEntity;
import com.ait.auth.domain.entitiy.UsersEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {

        factory.classMap(UsersEntity.class, UserDto.class)
                .byDefault()
                .register();

        factory.classMap(AuthoritiesEntity.class, AuthorityDto.class)
                .byDefault()
                .register();
    }
}
