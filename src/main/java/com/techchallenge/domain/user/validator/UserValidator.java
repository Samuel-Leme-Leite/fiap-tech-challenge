package com.techchallenge.domain.user.validator;

import com.techchallenge.domain.user.entity.User;

public interface UserValidator {
    void validateForCreate(User user);
    void validateForUpdate(User user);
    void validatePasswordChange(User user, String oldPassword, String newPassword);
}
