package com.ecommerce.user_service.dto.user;

import com.ecommerce.user_service.domain.enums.RoleEnumUser;

public record UserDto(
    Long id_user,
    String username,
    String email,
    RoleEnumUser role) {

}
