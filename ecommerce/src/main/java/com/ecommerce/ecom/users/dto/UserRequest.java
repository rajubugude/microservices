package com.ecommerce.ecom.users.dto;


import com.ecommerce.ecom.users.models.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
