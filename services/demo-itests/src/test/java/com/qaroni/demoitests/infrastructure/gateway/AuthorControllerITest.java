package com.qaroni.demoitests.infrastructure.gateway;

import com.qaroni.demoitests.support.JsonAssertion;
import com.qaroni.demoitests.support.JsonAssertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("integration"))
@DisplayName("Author Controller Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@JsonAssertions
class AuthorControllerITest {

    private static final String AUTHORS_URL = "/authors";
    private static final String CONTENT_TYPE = "application/json";

    public static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.2")
            .withDatabaseName("demo-itests")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(true);

    private MockMvc mockMvc;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("When the endpoint GET /authors is called, it should return a list of authors")
    @Test
    void findAll(JsonAssertion jsonAssertion) throws Exception {
        // Given

        // When
        MvcResult mvcResult = mockMvc.perform(get(AUTHORS_URL).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/findAllExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );

    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given an author id, when the endpoint GET /authors/{id} is called, it should return the author with the given id")
    @Test
    void findById(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long authorId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(AUTHORS_URL + "/" + authorId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/findByIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given an author id, when the endpoint GET /authors/{id} is called, it should return status 404")
    @Test
    void notFoundById(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long authorId = 100L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(AUTHORS_URL + "/" + authorId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertNotNull(mvcResult.getResponse()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/notFoundByIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"})
            }
    )
    @Order(2)
    @DisplayName("Given a request body, when the endpoint POST /authors is called, it should create a new author")
    @Test
    void create(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = "{\"documentId\":\"111111111\",\"fullName\":\"Author Test 1\"}";

        // When
        MvcResult mvcResult = mockMvc.perform(post(AUTHORS_URL).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(201, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/createExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }


    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given a request body with an existing author, when the endpoint POST /authors is called, it should return status 409")
    @Test
    void notCreated(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = "{\"documentId\":\"111111111\",\"fullName\":\"Author Test 1\"}";

        // When
        MvcResult mvcResult = mockMvc.perform(post(AUTHORS_URL).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(409, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/notCreatedExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"})
            }
    )
    @Order(3)
    @DisplayName("Given an author id and a request body, when the endpoint PUT /authors/{id} is called, it should update the author with the given id")
    @Test
    void update(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = "{\"documentId\":\"111111111\",\"fullName\":\"Author Test\"}";
        Long authorId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(put(AUTHORS_URL + "/" + authorId).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/updateExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"})
            }
    )
    @Order(3)
    @DisplayName("Given an author id and a request body, when the endpoint PUT /authors/{id} is called, it should return status 404")
    @Test
    void notFoundUpdate(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = "{\"documentId\":\"111111111\",\"fullName\":\"Author Test\"}";
        Long requestId = 100L;

        // When
        MvcResult mvcResult = mockMvc.perform(put(AUTHORS_URL + "/" + requestId).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(404, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("authors/notFoundUpdateExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }
}