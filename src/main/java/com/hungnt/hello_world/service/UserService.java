package com.hungnt.hello_world.service;

import com.hungnt.hello_world.dto.request.UserCreationRequest;
import com.hungnt.hello_world.dto.request.UserUpdateRequest;
import com.hungnt.hello_world.dto.response.UserResponse;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.exception.AppException;
import com.hungnt.hello_world.exception.ErrorCode;
import com.hungnt.hello_world.mapper.UserMapper;
import com.hungnt.hello_world.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {


        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        return userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

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
}
