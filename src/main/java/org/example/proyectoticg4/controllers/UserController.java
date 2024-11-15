package org.example.proyectoticg4.controllers;

import jakarta.validation.Valid;
import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.entities.User;
import org.example.proyectoticg4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations(@RequestParam String userId) {
        return userService.getReservations(userId);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody User user) {
        if (userService.existsByUserId(user.getUserId())) {
            throw new IllegalArgumentException("User with this email/username already exists");
        }

        userService.registerUser(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password) {
        User user = userService.getUserById(userId);

        if (userService.verifyPassword(password, user.getPassword())) {
            return "Login successful";
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
