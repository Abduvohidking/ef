package uz.authorizationapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import uz.authorizationapp.service.AuthService;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.LoginDto;
import uz.authorizationapp.upload.RegisterDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        ApiResponse response = authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
        ApiResponse response = authService.loginUser(loginDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
    @PostMapping("/logout")
    public HttpEntity<?> logoutUser(HttpServletRequest httpServletRequest) {
        ApiResponse response = authService.logoutUser(httpServletRequest);
        return ResponseEntity.status(response.isSuccess()? 200 : 400).body(response);
    }
}
