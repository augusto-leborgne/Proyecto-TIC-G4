package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.userId = :userID")
    User findByUserId(String userID);
}
