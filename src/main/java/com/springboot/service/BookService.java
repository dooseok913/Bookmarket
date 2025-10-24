package com.springboot.service;

import com.springboot.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookService {
    // 모든 도서 목록들을 가져오는 메소드
    List<Book> getAllBookList();
    Book getBookById(String bookId);
    List<Book> getBookListByCategory(String category);

    Set<Book> getBookListByFilter(Map<String , List<String>> filter);

    void setNewBook(Book book);
    void setUpdateBook(Book book);

    void setDeleteBook(String bookID);

}
