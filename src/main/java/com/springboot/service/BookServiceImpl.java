package com.springboot.service;

import com.springboot.domain.Book;
import com.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service  // 서비스 컴포넌트로 등록(Bean)
public class BookServiceImpl implements BookService{

    //private BookRepositoryImpl bookRepository = new BookRepositoryImpl();
    @Autowired  // 의존성 주입(DI)
    private BookRepository bookRepository;

    @Override
    //  implements BookService 이므로 , BookService 인터페이스에 정의된 메서드를 재정의한 것
    public List<Book> getAllBookList() {
        return bookRepository.getAllBookList();
    }

    @Override
    public Book getBookById(String bookId) {
        Book bookById = bookRepository.getBookById(bookId);

        return bookById;
    }

    @Override
    public List<Book> getBookListByCategory(String category){
        List<Book> booksByCategory = bookRepository.getBookListByCategory(category);
        return booksByCategory;
    }

    @Override
    public Set<Book> getBookListByFilter(Map<String , List<String>> filter) {
        Set<Book> booksByPublisher = new HashSet<>();
        Set<Book> booksByCategory = new HashSet<Book>();
        Set<String> booksByFilter = filter.keySet();
        List<Book> listOfBooks = bookRepository.getAllBookList();
        if(booksByFilter.contains("publisher")){
            for(int j=0; j<filter.get("publisher").size();j++) {
                String publisherName = filter.get("publisher").get(j);
                for(int i = 0 ; i < listOfBooks.size(); i++){
                    Book book =listOfBooks.get(i);
                    if(publisherName.equalsIgnoreCase(book.getPublisher()))
                        booksByPublisher.add(book);
                }
            }
        }
        if(booksByFilter.contains("category")){
            for(int i = 0; i<filter.get("category").size(); i++) {
                String category = filter.get("category").get(i);
                List<Book> list = bookRepository.getBookListByCategory(category);
                booksByCategory.addAll(list);
            }
        }
        booksByCategory.retainAll(booksByPublisher);
        return  booksByCategory;
    }

    @Override
    public void setNewBook(Book book) {

        bookRepository.setNewBook(book);
    }

    @Override
    public void setUpdateBook(Book book) {
        bookRepository.setUpdateBook(book);
    }

    @Override
    public void setDeleteBook(String bookID){
        bookRepository.setDeleteBook(bookID);
    }

}
