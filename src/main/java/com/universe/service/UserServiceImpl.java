package com.universe.service;

import com.universe.persistence.entity.RoleEntity;
import com.universe.persistence.entity.UserEntity;
import com.universe.persistence.repository.RoleRepository;
import com.universe.persistence.repository.UserRepository;
import com.universe.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserEntity> findAllUsers() {
        Iterable<UserEntity> usersIterable = userRepository.findAll();
        return StreamSupport.stream(usersIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public void addUser(UserEntity user) {

        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

}
