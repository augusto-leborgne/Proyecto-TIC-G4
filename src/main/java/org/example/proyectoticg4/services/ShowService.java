package org.example.proyectoticg4.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.proyectoticg4.entities.*;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.repositories.ShowRepository;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Transactional
    public Show createShowWithAvailableSeats(Movie movie, Hall hall, LocalDateTime showTime, Integer price) {
        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(showTime);
        show.setPrice(price);

        Show savedShow = showRepository.save(show);
        entityManager.detach(savedShow);

        List<Seat> seatsInHall = hall.getSeats();

        List<ShowSeatAvailability> showSeatAvailabilities = seatsInHall.stream()
                .map(seat -> {
                    ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
                    ShowSeatAvailabilityId seatAvailabilityId =
                            new ShowSeatAvailabilityId(savedShow.getShowCode(), seat.getseatId());

                    seatAvailability.setId(seatAvailabilityId);
                    seatAvailability.setShow(savedShow);
                    seatAvailability.setAvailable(true);
                    return seatAvailability;
                })
                .collect(Collectors.toList());

        showSeatAvailabilityRepository.saveAll(showSeatAvailabilities);
        showSeatAvailabilityRepository.flush();

        savedShow.setShowSeatAvailabilities(showSeatAvailabilities);
        return savedShow;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> findShowsByCinemaNumber(int cinemaNumber) {
        return showRepository.findShowsByCinemaNumber(cinemaNumber);
    }

    public List<Show> findShowsByMovieAndCinema(String movieId, Integer cinemaNumber) {
        return showRepository.findByMovieIdAndCinemaNumber(movieId, cinemaNumber);
    }

    public List<ShowSeatAvailability> findSeats(String movieId, Integer cinemaNumber, LocalDateTime showTime) {
        List<ShowSeatAvailability> seats = showRepository.findSeats(movieId, cinemaNumber, showTime);
        if (seats == null || seats.isEmpty()) {
            throw new ResourceNotFoundException("Seats not found for the provided criteria");
        }
        return seats;
    }

    public void deleteShow(Integer showId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found with ID: " + showId);
        }
        showRepository.deleteById(showId);
    }

    public void deleteAllShows() {
        showRepository.deleteAll();
    }
}
