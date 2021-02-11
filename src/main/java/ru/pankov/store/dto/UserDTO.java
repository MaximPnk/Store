package ru.pankov.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDTO {

    @Pattern(regexp = "[a-zA-Z0-9]+")
    @Size(min = 3, max = 12)
    private String username;

    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*()]+")
    @Size(min = 3, max = 12)
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+")
    private String email;

    @Pattern(regexp = "(\\+7|8)+[0-9]{10}")
    private String phone;

    public UserDTO(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
