package org.example.mypetstore.model;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String address;
}
