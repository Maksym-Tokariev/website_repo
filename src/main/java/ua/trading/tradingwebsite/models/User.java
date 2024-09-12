package ua.trading.tradingwebsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_t")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The field can't be empty")
    private String name;

    @Size(min = 8, message = "The field must have at least 8 characters")
    private String password;

    @NotBlank(message = "The field can't be empty")
    @Column(unique = true)
    private String email;

    @Transient
    private String confirmPassword;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    private boolean active;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role = new HashSet<>();

    private LocalDateTime dateOfRegistration;

    @PrePersist
    private void init() {
        dateOfRegistration = LocalDateTime.now();
    }
}
