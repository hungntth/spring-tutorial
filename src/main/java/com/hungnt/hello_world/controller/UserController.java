package com.hungnt.hello_world.controller;

import com.hungnt.hello_world.dto.request.ApiResponse;
import com.hungnt.hello_world.dto.request.UserCreationRequest;
import com.hungnt.hello_world.dto.request.UserUpdateRequest;
import com.hungnt.hello_world.entity.User;
import com.hungnt.hello_world.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
    return apiResponse;
    }

    @GetMapping
    List<User> getUsers(){
            return userService.findAllUser();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId) {
        return userService.detaiUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "Delete User Success!";
    }
}
