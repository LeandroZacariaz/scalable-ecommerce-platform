package com.ecommerce.user_service.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponseDto(String token) {

}
