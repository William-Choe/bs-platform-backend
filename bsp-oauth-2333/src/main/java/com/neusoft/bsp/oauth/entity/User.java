package com.neusoft.bsp.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String lastLogin;
    private String roleId;
    private Role role;
    private List<Permission> permissions;
    private String walletId;
}
