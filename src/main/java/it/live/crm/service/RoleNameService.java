package it.live.crm.service;

import it.live.crm.entity.RoleName;
import it.live.crm.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleNameService {
    ResponseEntity<ApiResponse> add(String roleName);

    List<RoleName> get();

    ResponseEntity<ApiResponse> update(Long id, String roleName);

    ResponseEntity<ApiResponse> delete(Long id);
}
