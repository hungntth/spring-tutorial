package com.hungnt.hello_world.controller;

import com.hungnt.hello_world.dto.request.ApiResponse;
import com.hungnt.hello_world.dto.request.UserCreationRequest;
import com.hungnt.hello_world.dto.request.UserUpdateRequest;
import com.hungnt.hello_world.dto.response.UserResponse;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
    return apiResponse;
    }

    @GetMapping("/")
    List<User> getUsers(){
            return userService.findAllUser();
    }

    @GetMapping("/{userId}")
    UserResponse detailUser(@PathVariable("userId") String userId) {
        return userService.detaiUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "Delete User Success!";
    }
}
