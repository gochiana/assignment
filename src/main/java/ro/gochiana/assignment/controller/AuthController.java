package ro.gochiana.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.gochiana.assignment.entity.User;
import ro.gochiana.assignment.model.LoginCredentials;
import ro.gochiana.assignment.repository.UserRepository;
import ro.gochiana.assignment.security.JWTUtil;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestBody User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("New user created");
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getEmail());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authenticationException) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
