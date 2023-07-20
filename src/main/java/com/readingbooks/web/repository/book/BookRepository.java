package com.readingbooks.web.repository.book;

import com.readingbooks.web.domain.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, SearchBookRepository {
    boolean existsByCategoryId(Long categoryId);

    boolean existsByBookGroupId(Long bookGroupId);

    List<Book> findByTitle(String title);
}
