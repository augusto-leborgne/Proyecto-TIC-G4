package org.example.proyectoticg4.repositories;

import org.example.proyectoticg4.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a User instance
        User user = new User();
        user.setUserId("user1");
        user.setPassword("password");

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Retrieve the user by ID
        Optional<User> foundUser = userRepository.findById("user1");

        // Assertions
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isEqualTo("user1");
        assertThat(foundUser.get().getPassword()).isEqualTo("password");
    }

    @Test
    public void testFindByUserId() {
        // Create a User instance
        User user = new User();
        user.setUserId("user2");
        user.setPassword("password2");

        // Save the user to the database
        userRepository.save(user);

        // Retrieve the user using the custom method
        User foundUser = userRepository.findByUserId("user2");

        // Assertions
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo("user2");
        assertThat(foundUser.getPassword()).isEqualTo("password2");
    }

    @Test
    public void testFindAll() {
        // Create User instances
        User user1 = new User();
        user1.setUserId("user3");
        user1.setPassword("password3");

        User user2 = new User();
        user2.setUserId("user4");
        user2.setPassword("password4");

        // Save users to the database
        userRepository.save(user1);
        userRepository.save(user2);

        // Retrieve all users
        Iterable<User> users = userRepository.findAll();

        // Assertions
        assertThat(users).hasSize(2);
    }

    @Test
    public void testDelete() {
        // Create a User instance
        User user = new User();
        user.setUserId("user5");
        user.setPassword("password5");

        // Save the user to the database
        userRepository.save(user);

        // Delete the user
        userRepository.deleteById("user5");

        // Verify the user has been deleted
        Optional<User> deletedUser = userRepository.findById("user5");
        assertThat(deletedUser).isNotPresent();
    }
}
