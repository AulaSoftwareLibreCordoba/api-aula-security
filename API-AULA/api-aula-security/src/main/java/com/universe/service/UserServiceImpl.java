package com.universe.service;

import com.universe.persistence.entity.RoleEntity;
import com.universe.persistence.entity.UserEntity;
import com.universe.persistence.repository.RoleRepository;
import com.universe.persistence.repository.UserRepository;
import com.universe.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.universe.service.EnvioEmailService;

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
    private EnvioEmailService envioEmailService;

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

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Enviar correo al administrador con los detalles del nuevo usuario
        String emailContent = "Se ha registrado un nuevo usuario con los siguientes detalles:\n" +
                "Username: " + user.getUsername() + "\n" +
                "Email: " + user.getEmail() + "\n";

        envioEmailService.sendEmail(
                "auladesoftwarelibre@gmail.com", // Destinatario
                "Nuevo Usuario Registrado",      // Asunto del correo
                emailContent                     // Cuerpo del mensaje con los detalles del usuario
        );
    }

}
