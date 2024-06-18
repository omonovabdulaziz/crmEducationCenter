package it.live.crm.service.impl;

import it.live.crm.entity.RoleName;
import it.live.crm.exception.MainException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.repository.RoleRepository;
import it.live.crm.service.RoleNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleNameServiceImpl implements RoleNameService {
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<ApiResponse> add(String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            throw new MainException("Blank or empty role name");
        }
        roleRepository.save(new RoleName(roleName));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Ok").build());
    }

    @Override
    public List<RoleName> get() {
        return roleRepository.findAll();
    }

    @Override
    public ResponseEntity<ApiResponse> update(Long id, String roleName) {
        RoleName role = roleRepository.findById(id).orElseThrow(() -> new MainException("Role not found"));
        role.setName(roleName);
        roleRepository.save(role);
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> delete(Long id) {
        roleRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }
}
