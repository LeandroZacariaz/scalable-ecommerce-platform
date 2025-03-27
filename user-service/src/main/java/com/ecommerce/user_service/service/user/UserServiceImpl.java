package com.ecommerce.user_service.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.user_service.domain.User;
import com.ecommerce.user_service.domain.enums.RoleEnumUser;
import com.ecommerce.user_service.dto.user.UserRegisterDto;
import com.ecommerce.user_service.mappers.user.UserMapper;
import com.ecommerce.user_service.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        User userCreated=userMapper.userRegisterDtoToUser(userRegisterDto);
        userCreated.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        userCreated.setRole(RoleEnumUser.USER);
        return userRepository.save(userCreated);
    }

    @Override
    public User getLoggingUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found.");
            }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User getUserById(Long id_user) {
        return userRepository.findById(id_user)
                  .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id_user));
    }

    

}
