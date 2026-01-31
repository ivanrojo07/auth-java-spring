package com.example.festapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El correo electronico no puede estar vacío.")
    @Email(message = "El correo electronico debe ser válido.")
    private String email;

    @NotBlank(message="La contraseña no puede estar vacía.")
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
