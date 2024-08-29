package ua.trading.tradingwebsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_t")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
