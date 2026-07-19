package com.ecommerce.controller;

import com.ecommerce.dto.request.RoleRequest;
import com.ecommerce.dto.response.RoleResponse;
import com.ecommerce.model.Role;
import com.ecommerce.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse roleResponse = roleService.createRole(request);
        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long roleId,
            @Valid @RequestBody RoleRequest request) {
        RoleResponse roleResponse = roleService.updateRole(roleId, request);
        return ResponseEntity.ok(roleResponse);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roleResponses = roleService.getAllRoles();
        return ResponseEntity.ok(roleResponses);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long roleId) {
        RoleResponse roleResponse = roleService.getRoleById(roleId);
        return ResponseEntity.ok(roleResponse);
    }
}