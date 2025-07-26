package com.techchallenge.domain.user.service;

import com.techchallenge.application.dto.request.UpdatePasswordRequest;
import com.techchallenge.application.dto.request.UpdateUserRequest;
import com.techchallenge.application.mapper.UserMapper;
import com.techchallenge.domain.user.entity.User;
import com.techchallenge.domain.user.exception.UserNotFoundException;
import com.techchallenge.domain.user.repository.UserRepository;
import com.techchallenge.domain.user.validator.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserValidator userValidator, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(User user) {
        userValidator.validateForCreate(user);
        user.setActive(true); // Ativa o usuário por padrão ao criar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o UserName: " + userName));
    }

    public User updateUser(Long id, UpdateUserRequest request){
        User existingUser = findById(id);
        User updatedUser = userMapper.toEntity(request, existingUser);
        userValidator.validateForUpdate(updatedUser);
        return userRepository.save(updatedUser);
    }


    public void deleteById(Long id) {
        User user = findById(id);
        user.setActive(false); // Soft delete
        userRepository.save(user);
    }

    public void changePassword(Long id, UpdatePasswordRequest updatePasswordRequest, String loggedInUserName) {
        User user = findById(id);
        userValidator.validatePasswordChange(user, updatePasswordRequest.oldPassword(), updatePasswordRequest.newPassword(), loggedInUserName);
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.newPassword()));
        userRepository.save(user);
    }

    public void activateUser(Long id) {
        User user = findById(id);
        user.setActive(true);
        userRepository.save(user);
    }

    public boolean isUserActive(Long id) {
        User user = findById(id);
        return user.isActive();
    }

    public boolean userNameExists(String userName) {
        return userRepository.existsByUserName(userName);
    }
}