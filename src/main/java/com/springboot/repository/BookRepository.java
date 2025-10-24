package com.springboot.repository;

import com.springboot.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookRepository
//        extends JpaRepository<Book, String>
{


//    List<Book> findAll(); // 모든 도서 목록들을 가져오는 메소드
//    Book findBybookId(String bookId);
//    List<Book> findByCategory(String category);

    List<Book> getAllBookList();
    Book getBookById(String bookId);
    List<Book> getBookListByCategory (String category);
    Set<Book> getBookListByFilter(Map<String, List<String>> filter);
    void setNewBook(Book book);
    long count();

    void setUpdateBook(Book book);

    void setDeleteBook(String bookID);




}
