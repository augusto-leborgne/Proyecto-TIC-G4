package org.example.proyectoticg4.dbd.repositories;

import org.example.proyectoticg4.dbd.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
