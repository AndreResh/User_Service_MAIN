package userservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import userservice.UserServiceApplication;
import userservice.configuration.ContainersEnvironment;
import userservice.domain.User;
import userservice.pojo.RegisterClass;
import userservice.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
class UserControllerTest extends ContainersEnvironment {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserController controller;

    @BeforeEach
    void setUp() {
    }

    @Test
    void statusCodeForbidden() {
        ResponseEntity<String> response2 = restTemplate
                .withBasicAuth("user@yandex.ru", "user")
                .getForEntity("/api/users", String.class);
        assertEquals(MediaType.APPLICATION_JSON, response2.getHeaders().getContentType());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());

    }

    @Test
    void findAll() {
        ResponseEntity<String> response1 = restTemplate
                .withBasicAuth("admin@yandex.ru", "admin")
                .getForEntity("/api/users", String.class);
        assertEquals(MediaType.APPLICATION_JSON, response1.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response1.getStatusCode());

    }

    @Test
    void findById() {
        User user = controller.findById(1L).getBody();
        assertEquals(1L, user.getId());
    }
}