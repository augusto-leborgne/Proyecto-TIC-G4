package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("Select image From Movie")
    List<byte[]> findAllImages();

}
