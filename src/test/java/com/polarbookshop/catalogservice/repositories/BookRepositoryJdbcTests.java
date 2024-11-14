package com.polarbookshop.catalogservice.repositories;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@DataJdbcTest // Identifies a test class that focuses on Spring Data JDBC components
@Import(DataConfig.class) // Imports the data configuration (needed to enable auditing)
@AutoConfigureTestDatabase(  // Disables the default behavior of relying on an embedded test database since we want to use Testcontainers
    replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration") // Enables the “integration” profile to load configuration from application-integration.yml
class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate; // A lower-level object to interact with the database

    @Test
    void findBookByIsbnWhenBookExists() {
        var bookIsbn = "1234567890";
        var book = Book.of(bookIsbn, "Title", "Author", 9.0, "Polarsophia");
        jdbcAggregateTemplate.insert(book); // JdbcAggregateTemplate is used to prepare the data targeted by the test.
        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
    }

    @Test
    void findAllBooks() {
        var book1 = Book.of("1234561235", "Title", "Author", 12.90, "Polarsophia");
        var book2 = Book.of("1234561236", "Another Title", "Author", 12.90, "Polarsophia");
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
            .filter(book -> book.isbn().equals(book1.isbn()) || book.isbn().equals(book2.isbn()))
            .collect(Collectors.toList())).hasSize(2);
    }
}