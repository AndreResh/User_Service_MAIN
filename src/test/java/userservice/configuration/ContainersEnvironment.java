package userservice.configuration;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import userservice.containers.TestPostgresContainer;

@Testcontainers
public class ContainersEnvironment {
    @Container
    private static final PostgreSQLContainer<?> container = TestPostgresContainer.getInstance();
}
