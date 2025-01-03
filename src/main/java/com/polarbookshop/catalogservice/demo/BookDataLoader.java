package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.repositories.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Profile("testdata") // Assigns the class to the testdata profile. It will be registered only when the testdata profile is active.
public class BookDataLoader {
    private final BookRepository bookRepository;
    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @EventListener(ApplicationReadyEvent.class)  // The test data generation is triggered when an ApplicationReadyEvent is sent — that is when the application startup phase is completed.
    public void loadBookTestData() {
        bookRepository.deleteAll();
        var book1 = Book.of("1234567891", "Northern Lights",
            "Lyra Silverstar", 9.90, "Polarsophia", Instant.now(), Instant.now(), 0);
        var book2 = Book.of("1234567892", "Polar Journey",
            "Iorek Polarson", 12.90, "Polarsophia", Instant.now(), Instant.now(), 0);
//        bookRepository.save(book1);
//        bookRepository.save(book2);
        bookRepository.saveAll(List.of(book1,book2));
    }
}
