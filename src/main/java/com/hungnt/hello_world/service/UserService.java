package com.hungnt.hello_world.service;

import com.hungnt.hello_world.dto.request.UserCreationRequest;
import com.hungnt.hello_world.dto.request.UserUpdateRequest;
import com.hungnt.hello_world.dto.response.UserResponse;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.enums.Role;
import com.hungnt.hello_world.exception.AppException;
import com.hungnt.hello_world.exception.ErrorCode;
import com.hungnt.hello_world.mapper.UserMapper;
import com.hungnt.hello_world.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {


        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        // user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUser() {
        log.info("In Method get User");
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.id == authentication.name")
    public UserResponse detaiUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"))) ;
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

//    public UserResponse getDetailByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//    }
}
