package com.ecommerce.user_service.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user_service.dto.user.UserDto;
import com.ecommerce.user_service.mappers.user.UserMapper;
import com.ecommerce.user_service.service.user.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id_user) {
        UserDto userDto=userMapper.userToUserDto(userService.getUserById(id_user));
        return ResponseEntity.ok(userDto);
    }
}
