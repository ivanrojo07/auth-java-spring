package com.example.festapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

    User findByEmail(String email);

}
