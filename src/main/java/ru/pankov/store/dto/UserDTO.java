package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.store.entity.User;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {

    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Login: only letters / numbers")
    @Size(min = 3, max = 12, message = "Login: size from 3 to 12")
    private String username;

    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()]+", message = "Password: only letters / numbers / special characters")
    @Size(min = 3, max = 12, message = "Password: size from 3 to 12")
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+", message = "Email: format test@test.com")
    private String email;

    @Pattern(regexp = "(\\+7|8)+[0-9]{10}", message = "Phone: format +79001234567 / 89001234567")
    private String phone;

    private LocalDateTime createdAt;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
    }
}
