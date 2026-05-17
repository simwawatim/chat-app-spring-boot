package com.example.chat.user.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OnlineUserService {

    private final Set<String> users = new HashSet<>();

    public void addUser(String email) {
        users.add(email);
    }

    public void removeUser(String email) {
        users.remove(email);
    }

    public List<String> getUsers() {
        return new ArrayList<>(users);
    }
}