package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = Book.of("1234567890", "Title", "Author", 9.90, "Polarsophia", Instant.now(), Instant.now(), 0);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var testTimeStamp = Instant.parse("2024-12-09T17:24:39.920634800Z");
        var content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "publisher": "Polarsophia",
                "createdDate": "2024-12-09T17:24:39.920634800Z",
                "lastModifiedDate": "2024-12-09T17:24:39.920634800Z",
                "version": 0
            }
            """;
        assertThat(json.parse(content))
            .usingRecursiveComparison()
            .isEqualTo(Book.of("1234567890", "Title", "Author", 9.90, "Polarsophia", testTimeStamp, testTimeStamp, 0));

    }
}
