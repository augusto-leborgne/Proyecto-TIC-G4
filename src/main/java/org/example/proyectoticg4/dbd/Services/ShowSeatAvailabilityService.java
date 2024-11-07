package org.example.proyectoticg4.dbd.Services;

import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailability;
import org.example.proyectoticg4.dbd.Entities.ShowSeatAvailabilityId;
import org.example.proyectoticg4.dbd.Repositories.ShowSeatAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowSeatAvailabilityService {

    @Autowired
    private ShowSeatAvailabilityRepository showSeatAvailabilityRepository;

    public List<ShowSeatAvailability> findSeatsByIds(List<ShowSeatAvailabilityId> seatIds) {
        return showSeatAvailabilityRepository.findAllById(seatIds);
    }
}
