package ru.pankov.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pankov.store.bean.JwtTokenUtil;
import ru.pankov.store.dto.JwtRequest;
import ru.pankov.store.dto.JwtResponse;
import ru.pankov.store.dto.UserDTO;
import ru.pankov.store.entity.User;
import ru.pankov.store.err.MarketError;
import ru.pankov.store.service.inter.UserService;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())), HttpStatus.BAD_REQUEST);
        }

        Optional<User> userFromBD = userService.findDuplicate(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone());
        if (userFromBD.isPresent()) {
            if (userDTO.getUsername().toLowerCase(Locale.ROOT).equals(userFromBD.get().getUsername().toLowerCase(Locale.ROOT))) {
                return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Username already exists"), HttpStatus.BAD_REQUEST);
            } else if (userDTO.getEmail().toLowerCase(Locale.ROOT).equals(userFromBD.get().getEmail().toLowerCase(Locale.ROOT))) {
                return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Email already exists"), HttpStatus.BAD_REQUEST);
            } else if (userDTO.getPhone().equals(userFromBD.get().getPhone())) {
                return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Phone already exists"), HttpStatus.BAD_REQUEST);
            }
        }

        userService.save(userDTO);
        return ResponseEntity.ok(200);
    }
}
