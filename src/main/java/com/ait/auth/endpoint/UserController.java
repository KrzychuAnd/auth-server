package com.ait.auth.endpoint;

import com.ait.auth.domain.dto.AddUserDto;
import com.ait.auth.domain.dto.UserDto;
import com.ait.auth.domain.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Principal get(final Principal principal) {
        return principal;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllUsers() {
        return usersService.findAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody AddUserDto addUserDto) {
        return usersService.addUser(addUserDto);
    }
}
