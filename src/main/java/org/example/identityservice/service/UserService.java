package org.example.identityservice.service;

import java.util.HashSet;
import java.util.List;

import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.request.UserUpdateRequest;
import org.example.identityservice.dto.response.UserResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.Role;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.mapper.UserMapper;
import org.example.identityservice.repository.RoleRepository;
import org.example.identityservice.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            //            throw new ArrayIndexOutOfBoundsException("ErrorCode.USER_EXISTED");
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userMapper.toUser(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        // user.setRoles(roles);

        return userRepository.save(user);
    }

    //    @PostAuthorize(returnObject)
    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GET_ALL_USERS')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //    @PostAuthorize("returnObject.username == authentication.name")
    // @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.warn("{} logging", name);
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        request.setPassword(encodePassword(request.getPassword()));
        userMapper.updateUser(user, request);
        user.setRoles(new HashSet<>(roleRepository.findAllById(request.getRoles())));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
