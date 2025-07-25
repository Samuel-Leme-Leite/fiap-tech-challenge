package com.techchallenge.domain.user.entity;

import com.techchallenge.domain.shared.entity.Address;
import com.techchallenge.domain.shared.entity.BaseEntity;
import com.techchallenge.domain.shared.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true) // Ensures that equals and hashCode methods consider fields from BaseEntity
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    private String lastName;

    @Column(name = "email", nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    @NotBlank
    private String userName;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Embedded
    private Address address;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Default to active

    @Column(name = "profile", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole profile = UserRole.CUSTOMER; // Default profile

    public String getName() {
        return firstName + " " + lastName;
    }
}
