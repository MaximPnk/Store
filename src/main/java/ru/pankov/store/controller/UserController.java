package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.store.dto.UserDTO;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public UserDTO getUser(Principal principal) {
        return userService.findUserDTOByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
