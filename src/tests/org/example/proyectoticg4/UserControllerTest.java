package org.example.proyectoticg4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.proyectoticg4.controllers.UserController;
import org.example.proyectoticg4.entities.Reservation;
import org.example.proyectoticg4.entities.User;
import org.example.proyectoticg4.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Test data
        User user1 = new User();
        user1.setUserId("user1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserId("user2");
        user2.setPassword("password2");

        List<User> users = Arrays.asList(user1, user2);

        // Mock configuration
        when(userService.getAllUsers()).thenReturn(users);

        // Execution and verification
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is("user1")))
                .andExpect(jsonPath("$[1].userId", is("user2")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        // Test data
        String userId = "user1";
        User user = new User();
        user.setUserId(userId);
        user.setPassword("password1");

        // Mock configuration
        when(userService.getUserById(userId)).thenReturn(user);

        // Execution and verification
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.password", is("password1")));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetReservations() throws Exception {
        // Test data
        String userId = "user1";
        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(1L);
        reservation1.setTotal(100);

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(2L);
        reservation2.setTotal(200);

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        // Mock configuration
        when(userService.getReservations(userId)).thenReturn(reservations);

        // Execution and verification
        mockMvc.perform(get("/api/users/reservations")
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].total", is(100.0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].total", is(200.0)));

        verify(userService, times(1)).getReservations(userId);
    }

    @Test
    public void testRegister() throws Exception {
        // Test data
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password1");

        // Mock configuration
        when(userService.existsByUserId("user1")).thenReturn(false);
        doNothing().when(userService).registerUser(Mockito.any(User.class));

        // Convert object to JSON
        String json = mapper.writeValueAsString(user);

        // Execution and verification
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        verify(userService, times(1)).existsByUserId("user1");
        verify(userService, times(1)).registerUser(Mockito.any(User.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        // Test data
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password1");

        // Mock configuration
        when(userService.existsByUserId("user1")).thenReturn(true);

        // Convert object to JSON
        String json = mapper.writeValueAsString(user);

        // Execution and verification
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User with this email/username already exists"));

        verify(userService, times(1)).existsByUserId("user1");
        verify(userService, times(0)).registerUser(Mockito.any(User.class));
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        // Test data
        String userId = "user1";
        String password = "password1";
        User user = new User();
        user.setUserId(userId);
        user.setPassword("hashedPassword");

        // Mock configuration
        when(userService.getUserById(userId)).thenReturn(user);
        when(userService.verifyPassword(password, "hashedPassword")).thenReturn(true);

        // Execution and verification
        mockMvc.perform(post("/api/users/login")
                        .param("userId", userId)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).verifyPassword(password, "hashedPassword");
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        // Test data
        String userId = "user1";
        String password = "wrongPassword";
        User user = new User();
        user.setUserId(userId);
        user.setPassword("hashedPassword");

        // Mock configuration
        when(userService.getUserById(userId)).thenReturn(user);
        when(userService.verifyPassword(password, "hashedPassword")).thenReturn(false);

        // Execution and verification
        mockMvc.perform(post("/api/users/login")
                        .param("userId", userId)
                        .param("password", password))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid credentials"));

        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).verifyPassword(password, "hashedPassword");
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Test data
        String userId = "user1";

        // Mock configuration
        doNothing().when(userService).deleteUser(userId);

        // Execution and verification
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }
}
