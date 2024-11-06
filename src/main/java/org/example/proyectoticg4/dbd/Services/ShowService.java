package org.example.proyectoticg4.dbd.Services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.proyectoticg4.dbd.Entities.*;
import org.example.proyectoticg4.dbd.Repositories.SeatRepository;
import org.example.proyectoticg4.dbd.Repositories.ShowRepository;
import org.example.proyectoticg4.dbd.Repositories.ShowSeatAvailabilityRepository;
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
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Transactional
    public Show createShowWithAvailableSeats(Movie movie, Hall hall, LocalDateTime showTime, Integer price) {
        try {
            Show show = new Show();
            show.setMovie(movie);
            show.setHall(hall);
            show.setShowTime(showTime);
            show.setPrice(price);

            // Save the show, then detach it to avoid session conflicts
            Show savedShow = showRepository.save(show);
            entityManager.detach(savedShow);

            List<Seat> seatsInHall = hall.getSeats(); // Ensure this gets the correct seats for the hall

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

            // Save all ShowSeatAvailability entries at once
            showSeatAvailabilityRepository.saveAll(showSeatAvailabilities);
            showSeatAvailabilityRepository.flush();  // Force flush to write to DB immediately

            savedShow.setShowSeatAvailabilities(showSeatAvailabilities);
            return savedShow;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Re-throw to handle in the controller
        }
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
        return showRepository.findSeats(movieId, cinemaNumber, showTime);
    }

    public void deleteShow(Integer showId) {
        showRepository.deleteById(showId);
    }
}
