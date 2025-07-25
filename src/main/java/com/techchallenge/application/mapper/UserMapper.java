package com.techchallenge.application.mapper;

import com.techchallenge.application.dto.request.CreateUserRequest;
import com.techchallenge.application.dto.request.UpdateUserRequest;
import com.techchallenge.application.dto.response.UserResponse;
import com.techchallenge.domain.shared.enums.UserRole;
import com.techchallenge.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        // Converter User → UserResponse
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserName(),
                user.isActive(),
                user.getCreatedAt(),
                user.getLastModifiedDate(),
                user.getProfile(),
                user.getAddress()
        );
    }

    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setUserName(request.userName());
        user.setPassword(request.password());
        user.setAddress(request.address());
        user.setActive(true);
        user.setProfile(request.profile() != null ? request.profile() : UserRole.CUSTOMER);
        return user;
    }

    public User toEntity(UpdateUserRequest request, User currentUser){
        currentUser.setFirstName(request.firstName());
        currentUser.setLastName(request.lastName());
        currentUser.setEmail(request.email());
        currentUser.setUserName(request.userName());
        currentUser.setPassword(currentUser.getPassword());
        currentUser.setAddress(request.address());
        currentUser.setProfile(request.profile());
        return currentUser;
    }
}
