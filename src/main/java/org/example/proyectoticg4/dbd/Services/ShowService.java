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
        // Step 1: Create a new Show instance
        Show show = new Show();
        show.setMovie(movie);
        show.setHall(hall);
        show.setShowTime(showTime);

        // Step 2: Save the Show to get an ID
        Show savedShow = showRepository.save(show);

        // Step 3: Fetch seats in the given hall
        List<Seat> seatsInHall = seatRepository.findByHall(hall);

        // Step 4: For each seat, create a ShowSeatAvailability and set available to true
        List<ShowSeatAvailability> showSeatAvailabilities = seatsInHall.stream()
                .map(seat -> {
                    ShowSeatAvailability seatAvailability = new ShowSeatAvailability();
                    seatAvailability.setId(new ShowSeatAvailabilityId(savedShow.getShowCode(), seat.getseatId()));
                    seatAvailability.setShow(savedShow);
                    seatAvailability.setAvailable(true);  // Default value
                    return seatAvailability;
                })
                .collect(Collectors.toList());

        // Step 5: Save all ShowSeatAvailability records
        showSeatAvailabilityRepository.saveAll(showSeatAvailabilities);

        // Step 6: Set the list in the show entity (optional)
        show.setShowSeatAvailabilities(showSeatAvailabilities);

        return show;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> findShowsByCinemaNumber(int cinemaNumber) {
        return showRepository.findShowsByCinemaNumber(cinemaNumber);
    }

    public void deleteShow(Integer showId) {
        showRepository.deleteById(showId);
    }
}
