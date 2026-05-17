package com.example.chat.user.controller;

import com.example.chat.response.ApiResponse;
import com.example.chat.user.DTOs.*;
import com.example.chat.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAll() {

        return new ApiResponse<>(
                "success",
                "Users fetched successfully",
                service.getAll(),
                200
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getById(@PathVariable Long id) {

        return new ApiResponse<>(
                "success",
                "User fetched successfully",
                service.getById(id),
                200
        );
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserResponseDTO> patch(@PathVariable Long id,
                                            @RequestBody UserPatchDTO dto) {

        return new ApiResponse<>(
                "success",
                "User updated successfully",
                service.patch(id, dto),
                200
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        service.delete(id);

        return new ApiResponse<>(
                "success",
                "User deleted successfully",
                null,
                200
        );
    }


    @PatchMapping("/{id}/profile-picture")
    public ApiResponse<UserResponseDTO> uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return new ApiResponse<>(
                "success",
                "Profile picture uploaded",
                service.uploadProfilePicture(id, file),
                200
        );
    }
}