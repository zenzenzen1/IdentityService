package org.example.identityservice.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.request.UserUpdateRequest;
import org.example.identityservice.dto.response.UserResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.mapper.UserMapper;
import org.example.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
//            throw new ArrayIndexOutOfBoundsException("ErrorCode.USER_EXISTED");
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userMapper.toUser(request);
//        user.setUsername(request.getUsername());  
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
        
        
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponse getUser(String id){
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
//        return UserResponse.builder()
//                .id(id)
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .dob(user.getDob())
//                .build();
    }
    
    public String encodePassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        request.setPassword(encodePassword(request.getPassword()));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
    
}
