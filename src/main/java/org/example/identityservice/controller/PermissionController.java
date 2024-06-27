package org.example.identityservice.controller;

import java.util.List;

import org.example.identityservice.dto.request.PermissionRequest;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.response.PermissionResponse;
import org.example.identityservice.enums.ResponseCode;
import org.example.identityservice.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/permissions"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody final PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(permissionRequest))
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @GetMapping()
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermission())
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.deletePermission(permission);
        return ApiResponse.<Void>builder().code(ResponseCode.SUCCESS.getCode()).build();
    }
}
