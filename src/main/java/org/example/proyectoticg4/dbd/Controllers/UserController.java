package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.User;
import org.example.proyectoticg4.dbd.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.checkIfUserExists(user.getUserId())){
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

//    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.getUserById(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userService.verifyPassword(password, user.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
