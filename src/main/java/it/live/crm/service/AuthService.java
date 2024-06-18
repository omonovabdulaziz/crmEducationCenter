package it.live.crm.service;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse> login(LoginDTO loginDTO);

}
