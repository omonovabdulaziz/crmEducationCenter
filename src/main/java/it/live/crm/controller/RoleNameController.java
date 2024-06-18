package it.live.crm.controller;

import it.live.crm.entity.RoleName;
import it.live.crm.payload.ApiResponse;
import it.live.crm.service.RoleNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-name")
@RequiredArgsConstructor
public class RoleNameController {
    private final RoleNameService roleNameService;

    @CacheEvict(value = "List<RoleName>" , key = "'allRoles'")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestParam String roleName) {
        return roleNameService.add(roleName);
    }


    @Cacheable(value = "List<RoleName>", key = "'allRoles'")
    @GetMapping("/get")
    public List<RoleName> get() {
        return roleNameService.get();
    }

    @Caching(put = {
            @CachePut(value = "List<RoleName>", key = "'allRoles'")},
            evict = {@CacheEvict(value = "List<RoleName>", allEntries = true)}
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestParam String roleName) {
        return roleNameService.update(id, roleName);
    }

    @CacheEvict(value = "List<RoleName>" , key = "'allRoles'")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return roleNameService.delete(id);
    }

}
