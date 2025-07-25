package com.techchallenge.domain.user.validator;

import com.techchallenge.domain.user.entity.User;
import com.techchallenge.domain.user.exception.InvalidPasswordException;
import com.techchallenge.domain.user.exception.UserAlreadyExistsException;
import com.techchallenge.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserValidatorImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validateForCreate(User user){
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new UserAlreadyExistsException("Usuário já existe com o UserName: " + user.getUserName());
        }
    }

    @Override
    public void validateForUpdate(User user) {
        User existingUser = userRepository.findByUserName(user.getUserName()).orElse(null);
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new UserAlreadyExistsException("Usuário já existe...");
        }
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        if (user.getPassword() != null && !user.getPassword().matches(passwordPattern)) {
            throw new InvalidPasswordException("A senha deve conter pelo menos 6 caracteres, uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.");
        }
    }

    @Override
    public void validatePasswordChange(User user, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new InvalidPasswordException("Nova senha não pode ser vazia");
        }
        if(user.getPassword() == null || !passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException("Senha antiga inválida");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new InvalidPasswordException("A nova senha não pode ser igual à senha antiga");
        }
    }
}
