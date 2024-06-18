package it.live.crm.service.impl;

import it.live.crm.entity.User;
import it.live.crm.exception.ForbiddenException;
import it.live.crm.exception.NotFoundException;
import it.live.crm.jwt.JwtProvider;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.LoginDTO;
import it.live.crm.repository.UserRepository;
import it.live.crm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public ResponseEntity<ApiResponse> login(LoginDTO loginDTO) {
        User user = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber()).orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new ForbiddenException("Wrong password");
        String jwt = jwtProvider.generateToken(user);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwt);
        map.put("roleNames", user.getRoleName());
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Ok").object(map).build());
    }
}
