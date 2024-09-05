package org.example.proyectoticg4.dbd.Services;// src/main/java/com/example/cinemaapp/service/HallService.java


import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Exceptions.ResourceNotFoundException;
import org.example.proyectoticg4.dbd.Repositories.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    // Obtener todas las salas
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    // Obtener una sala por número de sala y cine
    public Hall getHallById(int hallNumber, int cinemaNumber) {
        return hallRepository.findByHallNumberAndCinemaNumber(hallNumber, cinemaNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Hall with number " + hallNumber + " in cinema " + cinemaNumber + " not found"));
    }

    // Crear una nueva sala
    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

//    // Actualizar una sala existente
//    public Hall updateHall(int hallNumber, int cinemaNumber, Hall hallDetails) {
//        Hall existingHall = getHallById(hallNumber, cinemaNumber);
//        existingHall.setCapacity(hallDetails.getCapacity());
//        return hallRepository.save(existingHall);
//    }

    // Eliminar una sala
    public void deleteHall(int hallNumber, int cinemaNumber) {
        Hall existingHall = getHallById(hallNumber, cinemaNumber);
        hallRepository.delete(existingHall);
    }
}
