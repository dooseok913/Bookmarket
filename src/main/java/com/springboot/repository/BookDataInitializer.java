package com.springboot.repository;

import com.springboot.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BookDataInitializer implements CommandLineRunner {

    private  final BookRepository bookRepository;




    @Override
    public void run(String ... args) throws Exception {
        Book book1 = new Book();
        book1.setBookId("ISBN1");
        book1.setName("Java Programming");
        book1.setUnitPrice(new java.math.BigDecimal("29.99"));
        book1.setAuthor("John Doe");
        book1.setDescription("A comprehensive guide to Java programming.");
        book1.setPublisher("Tech Books Publishing");
        book1.setCategory("Programming");
        book1.setUnitsInStock(100);
        book1.setReleaseDate("2023-01-15");
        book1.setBook_condition("New");
        book1.setFileName("ISBN1234.jpg");

        Book book2 = new Book();
        book2.setBookId("ISBN2");
        book2.setName("Spring Boot in Action");
        book2.setUnitPrice(new java.math.BigDecimal("39.99"));
        book2.setAuthor("Jane Smith");
        book2.setDescription("Learn Spring Boot with practical examples.");
        book2.setPublisher("Tech Books Publishing");
        book2.setCategory("Programming");
        book2.setUnitsInStock(50);
        book2.setReleaseDate("2023-03-10");
        book2.setBook_condition("New");
        book2.setFileName("ISBN1235.jpg");

        Book book3 = new Book();
        book3.setBookId("ISBN3");
        book3.setName ("안드로이드 프로그래밍");
        book3.setUnitPrice(new BigDecimal(36000));
        book3.setAuthor("송미영");
        book3.setDescription(
                "안드로이드의 기본 개념을 체계적으로 익히고, 이를 실습 예제를 통해 익힙니다. 기본 개념과 사용법을 스스로 실전에 적용하는 방법을 학습한 다음 실습 예제와 응용 예제를 통해 실전 프로젝트 응용력을 키웁니다.");
        book3.setPublisher("길벗");
        book3.setCategory("IT교육교재");
        book3.setUnitsInStock(1000);
        book3.setReleaseDate("2023/06/30");
        book3.setBook_condition("New");
        book3.setFileName("ISBN1236.jpg");

        if(bookRepository.count() == 0) {
            bookRepository.setNewBook(book1);
            bookRepository.setNewBook(book2);
            bookRepository.setNewBook(book3);
            System.out.println(" 샘플 도서 3권이 DB에 자동 등록되었습니다.");


        } else {
            System.out.println("기존 db에 데이터가 존재하므로, 추가삽입이 생략");
        }
    }
}
