package userservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import userservice.configuration.ContainersEnvironment;
import userservice.domain.Role;
import userservice.domain.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest extends ContainersEnvironment {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role role=roleRepository.findRoleByName("ROLE_USER");
        userRepository.save(User.builder().email("abob@yandex.ru").password("adbbbb").roles(List.of(role)).build());
    }

    @Test
    void findUserByEmail() {
        User user=userRepository.findUserByEmail("abob@yandex.ru");
        assertEquals("abob@yandex.ru", user.getEmail());
    }

    @Test
    void findAll() {
        List<User> users=userRepository.findAll();
        assertEquals(3, users.size());
    }
    @Test
    void delete(){
        userRepository.deleteById(1L);
        assertEquals(2, userRepository.findAll().size());
    }
}