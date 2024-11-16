package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.*;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.ShowRepository;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @InjectMocks
    private ShowService showService;

    @Captor
    private ArgumentCaptor<List<ShowSeatAvailability>> seatAvailabilityCaptor;

    @Test
    public void testCreateShowWithAvailableSeats_Success() {
        // Datos de prueba
        Movie movie = new Movie();
        movie.setMovieId("M1");

        Seat seat1 = new Seat();
        Seat seat2 = new Seat();
        List<Seat> seatsInHall = Arrays.asList(seat1, seat2);

        Hall hall = new Hall();
        hall.setSeats(seatsInHall);

        LocalDateTime showTime = LocalDateTime.now();
        Integer price = 100;

        Show savedShow = new Show();
        savedShow.setShowCode(1L);
        savedShow.setMovie(movie);
        savedShow.setHall(hall);
        savedShow.setShowTime(showTime);
        savedShow.setPrice(price);

        // Configuración de mocks
        when(showRepository.save(any(Show.class))).thenReturn(savedShow);

