package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Reservation;
import org.example.proyectoticg4.dbd.Entities.User;
import org.example.proyectoticg4.dbd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public boolean verifyPassword(String password1, String password2) {
        return password1.equals(password2);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findByUserId(id);
    }

    public List<Reservation> getReservations(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null || user.getReservations() == null) {
            return null;
        }else {
            return user.getReservations();
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
