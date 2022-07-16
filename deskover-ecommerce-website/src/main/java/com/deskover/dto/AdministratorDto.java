package com.deskover.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.deskover.entity.AdminAuthority;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDto implements Serializable {
    private static final long serialVersionUID = 1826221885734209898L;
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String avatar;
    private Boolean actived;
    private Timestamp lastLogin;
//    private Set<AdminAuthority> authorities;
}
