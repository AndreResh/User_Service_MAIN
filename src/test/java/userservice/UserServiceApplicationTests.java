package userservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import userservice.configuration.ContainersEnvironment;
import userservice.repository.UserRepository;
import userservice.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests extends ContainersEnvironment {
    @Autowired
    UserService repository;

    @Test
    void findAll() {
        assertEquals(2, repository.getAllUsers().size());
    }

    @Test
    void contextLoads() {
        System.out.println("Context loads!!!");
    }
}
