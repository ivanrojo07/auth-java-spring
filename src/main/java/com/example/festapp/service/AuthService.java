package com.example.festapp.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.festapp.dto.LoginRequest;
import com.example.festapp.dto.RegisterRequest;
import com.example.festapp.exception.InvalidCredentialsException;
import com.example.festapp.exception.UserAlreadyExistsException;
import com.example.festapp.model.Role;
import com.example.festapp.model.User;
import com.example.festapp.repository.RoleRepository;
import com.example.festapp.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        // 1. Validar que el usuario existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }

        // 1a. Validar username exista para generar otro
        String username = request.getUsername();
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            // Generar otro username
            username = request.getUsername() + System.currentTimeMillis();
        }

        // 2. Obtener ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("ROLE_USER no encontrado"));

        // 3. Crear el usuario
        User user = new User();
        user.setUsername(username);
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        user.setEnabled(true);

        // 4. Guardar el usuario
        userRepository.save(user);
    }


    public void login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new InvalidCredentialsException("Credenciales no validas. Err 1"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciales no validas. Err 2");
        }

        if(!user.isEnabled()) {
            throw new InvalidCredentialsException("Credenciales no validas. Err 3");
        }

        // TODO Generar y regresar el JWT
        
    }

}
