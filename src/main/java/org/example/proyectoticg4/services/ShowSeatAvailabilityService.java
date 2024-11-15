package org.example.proyectoticg4.services;

import org.example.proyectoticg4.entities.ShowSeatAvailability;
import org.example.proyectoticg4.entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.repositories.ShowSeatAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowSeatAvailabilityService {

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    public List<ShowSeatAvailability> findSeatsByIds(List<ShowSeatAvailabilityId> seatIds) {
        return showSeatAvailabilityRepository.findAllById(seatIds);
    }
}
