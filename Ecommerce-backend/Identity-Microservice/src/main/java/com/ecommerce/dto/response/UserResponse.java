package com.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Boolean enabled;

    private RoleResponse role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}