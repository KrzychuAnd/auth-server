package com.ait.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserDto {
    private String username;
    private String oldPassword;
    private String newPassword;
    private Integer enabled;
}
