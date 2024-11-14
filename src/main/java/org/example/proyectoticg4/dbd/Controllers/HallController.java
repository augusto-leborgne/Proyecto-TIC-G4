package org.example.proyectoticg4.dbd.Controllers;

import org.example.proyectoticg4.dbd.Entities.Hall;
import org.example.proyectoticg4.dbd.Entities.HallId;
import org.example.proyectoticg4.dbd.Services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{hallNumber}/{cinemaNumber}")
    public Hall getHallById(@PathVariable Integer hallNumber,
                            @PathVariable Integer cinemaNumber) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        return hallService.getHallById(hallId);
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallService.createHall(hall);
    }

    @PutMapping("/{hallNumber}/{cinemaNumber}")
    public Hall updateHall(@PathVariable Integer hallNumber,
                           @PathVariable Integer cinemaNumber,
                           @RequestBody Hall updatedHall) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        return hallService.updateHall(hallId, updatedHall);
    }

    @DeleteMapping("/{hallNumber}/{cinemaNumber}")
    public void deleteHall(@PathVariable Integer hallNumber,
                           @PathVariable Integer cinemaNumber) {
        HallId hallId = new HallId(hallNumber, cinemaNumber);
        hallService.deleteHall(hallId);
    }
}
