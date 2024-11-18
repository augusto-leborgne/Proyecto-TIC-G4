package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.User;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        User user = new User();
        when(userRepository.findByUserId("U1")).thenReturn(user);
        User foundUser = userService.getUserById("U1");
        assertNotNull(foundUser);
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findByUserId("U1")).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("U1"));
    }

    @Test
    void deleteUser_ShouldDelete_WhenUserExists() {
        when(userRepository.existsById("U1")).thenReturn(true);
        userService.deleteUser("U1");
        verify(userRepository, times(1)).deleteById("U1");
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.existsById("U1")).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser("U1"));
    }
}
