package com.emir.ecommerce.auth_service.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String username;
    private String password;
    private String role;



}
