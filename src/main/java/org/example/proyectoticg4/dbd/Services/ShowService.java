package org.example.proyectoticg4.dbd.Services;

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

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    @Transactional
    public Show createShowWithAvailableSeats(Movie movie, Hall hall, LocalDateTime showTime) {
        try {
            Show show = new Show();
            show.setMovie(movie);
            show.setHall(hall);
            show.setShowTime(showTime);

            Show savedShow = showRepository.save(show);
            List<Seat> seatsInHall = hall.getSeats(); // Ensure this gets the correct seats for the hall

            List<ShowSeatAvailability> showSeatAvailabilities = seatsInHall.stream()
                    .map(seat -> {
                        ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
                        seatAvailability.setId(new ShowSeatAvailabilityId(savedShow.getShowCode(), seat.getseatId()));
                        seatAvailability.setShow(savedShow); // Ensure this references the correct show
                        seatAvailability.setAvailable(true);
                        return seatAvailability;
                    })
                    .collect(Collectors.toList());

            showSeatAvailabilityRepository.saveAll(showSeatAvailabilities);
            show.setShowSeatAvailabilities(showSeatAvailabilities);
            return show;
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

    public List<Show> findShowsByMovieAndCinema(Integer movieId, Integer cinemaNumber) {
        return showRepository.findByMovieIdAndCinemaNumber(movieId, cinemaNumber);
    }

    public void deleteShow(Integer showId) {
        showRepository.deleteById(showId);
    }
}
