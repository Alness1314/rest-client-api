package com.alness.restclient.common.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private Boolean sendExpirationAlert;
    private String profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean erased;
}
