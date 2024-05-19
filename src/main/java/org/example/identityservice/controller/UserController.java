package org.example.identityservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.request.UserUpdateRequest;
import org.example.identityservice.dto.response.UserResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/users", "/users/"})
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;
    
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<User>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {} ", authentication.getName());
        log.info("roles: {}", authentication.getAuthorities());
        
        return ApiResponse.<List<User>>builder()
                .result(userService.getUsers())
                .code(1000)
                .build();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId)
    {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }

}
