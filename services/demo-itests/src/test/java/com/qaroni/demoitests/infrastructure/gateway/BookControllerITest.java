package com.qaroni.demoitests.infrastructure.gateway;

import com.qaroni.demoitests.support.json.JsonAssertion;
import com.qaroni.demoitests.support.json.JsonAssertions;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("integration"))
@DisplayName("Book Controller Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@JsonAssertions
public class BookControllerITest {

    private static final String BOOKS_URL = "/books";
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
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("When the endpoint GET /books is called, it should return a list of books")
    @Test
    void findAll(JsonAssertion jsonAssertion) throws Exception {
        // Given

        // When
        MvcResult mvcResult = mockMvc.perform(get(BOOKS_URL).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/findAllExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given a book id, when the endpoint GET /books/{id} is called, it should return the book information")
    @Test
    void findById(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long bookId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(BOOKS_URL + "/" + bookId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/findByIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given a book id, when the endpoint GET /books/{id} is called, it should return status 404")
    @Test
    void notFoundById(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long bookId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(BOOKS_URL + "/" + bookId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(404, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/notFoundByIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/init.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given an author id, when the endpoint GET /books/author/{id} is called, it should return the list of books by the author")
    @Test
    void findByAuthorId(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long authorId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(BOOKS_URL + "/author/" + authorId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/findByAuthorIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"})
            }
    )
    @Order(1)
    @DisplayName("Given an author id, when the endpoint GET /books/author/{id} is called, it should return status 404")
    @Test
    void notFoundAuthorId(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long authorId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(get(BOOKS_URL + "/author/" + authorId).contentType(CONTENT_TYPE))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(404, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/notFoundAuthorIdExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
            }
    )
    @Order(2)
    @DisplayName("Given a request body, when the endpoint POST /books is called, it should create a new book")
    @Test
    void create(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = """
                {
                    "title": "Book Test 1",
                    "isbn": "111111111",
                    "authorIds": [1, 2]
                }""";

        // When
        MvcResult mvcResult = mockMvc.perform(post(BOOKS_URL).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(201, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/createExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/init.sql"})
            }
    )
    @Order(2)
    @DisplayName("Given a request body with an existing book, when the endpoint POST /books is called, it should return status 409")
    @Test
    void notCreated(JsonAssertion jsonAssertion) throws Exception {
        // Given
        String requestBody = """
                {
                    "title": "Book Test 1",
                    "isbn": "111111111",
                    "authorIds": [1, 2]
                }""";

        // When
        MvcResult mvcResult = mockMvc.perform(post(BOOKS_URL).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(409, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/notCreatedExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/authors/init.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/init.sql"})
            }
    )
    @Order(3)
    @DisplayName("Given a request body, when the endpoint PUT /books/{id} is called, it should update the book information")
    @Test
    void update(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long bookId = 1L;
        String requestBody = """
                {
                    "title": "Book Test",
                    "isbn": "111111111",
                    "authorIds": [1, 2]
                }""";

        // When
        MvcResult mvcResult = mockMvc.perform(put(BOOKS_URL + "/" + bookId).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/updateExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }

    @SqlGroup(
            value = {
                    @Sql(scripts = {"classpath:db/authors/clean.sql"}),
                    @Sql(scripts = {"classpath:db/authors/create.sql"}),
                    @Sql(scripts = {"classpath:db/books/clean.sql"}),
                    @Sql(scripts = {"classpath:db/books/create.sql"})
            }
    )
    @Order(3)
    @DisplayName("Given a book id and a request body, when the endpoint PUT /books/{id} is called, it should return status 404")
    @Test
    void notFoundUpdate(JsonAssertion jsonAssertion) throws Exception {
        // Given
        Long bookId = 1L;
        String requestBody = """
                {
                    "title": "Book Test",
                    "isbn": "111111111",
                    "authorIds": [1, 2]
                }""";

        // When
        MvcResult mvcResult = mockMvc.perform(put(BOOKS_URL + "/" + bookId).contentType(CONTENT_TYPE).content(requestBody))
                .andReturn();

        // Then
        assertAll(
                () -> assertEquals(404, mvcResult.getResponse().getStatus()),
                () -> assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType()),
                () -> jsonAssertion.assertEquals("books/notFoundUpdateExpectedResponse.json", mvcResult.getResponse().getContentAsString())
        );
    }
}
