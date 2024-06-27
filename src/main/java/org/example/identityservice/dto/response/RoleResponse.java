package org.example.identityservice.dto.response;

import java.util.Set;

import org.example.identityservice.entity.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RoleResponse {
    private String name, description;
    private Set<Permission> permissions;
}
