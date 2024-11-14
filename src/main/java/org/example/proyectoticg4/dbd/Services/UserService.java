package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.Reservation;
import org.example.proyectoticg4.dbd.Entities.User;
import org.example.proyectoticg4.dbd.Exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.dbd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean existsByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    public boolean verifyPassword(String password1, String password2) {
        return password1.equals(password2);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return Optional.ofNullable(userRepository.findByUserId(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public List<Reservation> getReservations(String userId) {
        User user = getUserById(userId);
        if (user.getReservations() == null || user.getReservations().isEmpty()) {
            throw new ResourceNotFoundException("No reservations found for user ID: " + userId);
        }
        return user.getReservations();
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
