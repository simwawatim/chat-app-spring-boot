package com.example.chat.user.service;

import com.example.chat.exception.AlreadyExistsException;
import com.example.chat.exception.ResourceNotFoundException;
import com.example.chat.security.JwtUtil;
import com.example.chat.user.DTOs.*;
import com.example.chat.user.entity.User;
import com.example.chat.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
    this.repo = repo;
    this.encoder = encoder;
    this.jwtUtil = jwtUtil;
}
    // CREATE
    public UserResponseDTO create(UserRequestDTO dto) {

        if (repo.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        if (repo.existsByUsername(dto.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(encoder.encode(dto.getPassword()));

        return map(repo.save(user));
    }
    // GET ALL
    public List<UserResponseDTO> getAll() {
        return repo.findAll().stream().map(this::map).toList();
    }

    // GET ONE
    public UserResponseDTO getById(Long id) {
        return map(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    // UPDATE
    public UserResponseDTO patch(Long id, UserPatchDTO dto) {

        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }

        if (dto.getPassword() != null) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }

        return map(repo.save(user));
    }
    // DELETE
    public void delete(Long id) {

    User user = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    repo.delete(user);
}

    private UserResponseDTO map(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePictureUrl()
        );
    }

    public UserResponseDTO uploadProfilePicture(Long id, MultipartFile file) throws IOException {

    User user = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    String uploadDir = System.getProperty("user.home") + "/chat-app/uploads/";

    File dir = new File(uploadDir);
    if (!dir.exists()) {
        dir.mkdirs();
    }

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

    File destination = new File(uploadDir + fileName);

    file.transferTo(destination);

    user.setProfilePictureUrl("/uploads/" + fileName);

    return map(repo.save(user));
}

    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePictureUrl(),
                token
        );
    }
}