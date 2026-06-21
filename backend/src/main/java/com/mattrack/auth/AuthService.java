package com.mattrack.auth;

import com.mattrack.auth.dto.AuthResponse;
import com.mattrack.auth.dto.LoginRequest;
import com.mattrack.auth.dto.MeResponse;
import com.mattrack.auth.dto.RegisterRequest;
import com.mattrack.security.JwtService;
import com.mattrack.sport.SportType;
import com.mattrack.user.User;
import com.mattrack.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setBelt(request.belt());
        user.setWeight(request.weight());
        user.setAcademy(request.academy());
        user.setPrimarySport(request.primarySport() == null ? SportType.JIU_JITSU : request.primarySport());

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return new AuthResponse(token);
    }

    public MeResponse me(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new MeResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBelt(),
                user.getWeight(),
                user.getAcademy(),
                user.getPrimarySport(),
                user.getRole()
        );
    }
}
