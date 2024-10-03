package org.example.proyectoticg4.dbd.Repositories;

import org.example.proyectoticg4.dbd.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userID);
}
