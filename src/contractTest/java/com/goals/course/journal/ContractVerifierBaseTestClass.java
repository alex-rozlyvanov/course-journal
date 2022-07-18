package com.goals.course.journal;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dao.enums.Roles;
import com.goals.course.journal.dao.repository.FileRepository;
import com.goals.course.journal.dto.RoleDTO;
import com.goals.course.journal.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("contract-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(initializers = ContractVerifierBaseTestClass.Initializer.class)
@DirtiesContext
@AutoConfigureMessageVerifier
class ContractVerifierBaseTestClass {

    public static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.0")
            .withDatabaseName("course_journal")
            .withUsername("sa")
            .withPassword("sa");
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private FileRepository mockFileRepository;
    @Value("${app.jwt.access.secret}")
    private String accessTokenSecret;
    @Value("${app.jwt.access.expiration}")
    private Duration accessTokenExpiration;
    @Value("${app.jwt.issuer}")
    private String jwtIssuer;
    @Value("${app.admin.username}")
    private String adminUsername;
    @Value("${app.admin.firstname}")
    private String adminFirstname;
    @Value("${app.admin.lastname}")
    private String adminLastname;

    @BeforeEach
    public void setup() {
        setupFile();
        RestAssuredWebTestClient.applicationContextSetup(applicationContext);
    }

    private void setupFile() {
        final var fileEntity = new FileEntity()
                .setContentType("application/pdf")
                .setLessonId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .setStudentId(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        when(mockFileRepository.findById(any(UUID.class))).thenReturn(Mono.just(fileEntity));
    }

    public String authToken() {
        final var admin = UserDTO.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .username(adminUsername)
                .firstName(adminFirstname)
                .lastName(adminLastname)
                .roles(buildRoles())
                .build();
        return "Bearer %s".formatted(generateAccessToken(admin));
    }

    private List<RoleDTO> buildRoles() {
        return Stream.of(Roles.values())
                .map(r -> RoleDTO.builder().title(r.name()).build())
                .toList();
    }

    public String generateAccessToken(final UserDTO userDTO) {
        final var claims = buildClaims(userDTO);

        return Jwts.builder()
                .setSubject(userDTO.getUsername())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS256, accessTokenSecret)
                .addClaims(claims)
                .compact();
    }

    private Map<String, Object> buildClaims(final UserDTO userDTO) {
        final var roles = userDTO.getRoles();
        return Map.of("userId", userDTO.getId().toString(), "roles", roles);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();
            TestPropertyValues.of(
                    "spring.r2dbc.url=" + "r2dbc:postgresql://%s:%s/%s".formatted(postgreSQLContainer.getHost(), postgreSQLContainer.getFirstMappedPort(), postgreSQLContainer.getDatabaseName()),
                    "spring.r2dbc.username=" + postgreSQLContainer.getUsername(),
                    "spring.r2dbc.password=" + postgreSQLContainer.getPassword(),
                    "spring.flyway.url=jdbc:postgresql://%s:%s/%s".formatted(postgreSQLContainer.getHost(), postgreSQLContainer.getFirstMappedPort(), postgreSQLContainer.getDatabaseName()),
                    "spring.flyway.user=${spring.r2dbc.username}",
                    "spring.flyway.password=${spring.r2dbc.password}"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
