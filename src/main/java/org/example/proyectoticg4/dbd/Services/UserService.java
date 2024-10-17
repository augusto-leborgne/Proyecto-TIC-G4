package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.User;
import org.example.proyectoticg4.dbd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public boolean checkIfUserExists(String email) {
        return userRepository.findByUserId(email).isPresent();
    }

    public boolean verifyPassword(String password1, String password2) {
        if (password1.equals(password2)){
            return true;
        }else{
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
