package com.springboot.repository;

import com.springboot.domain.Book;
import com.springboot.exception.BookIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository // 레포지토리 컴포넌트로 등록(Bean)
public class   BookRepositoryImpl implements BookRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private List<Book> listOfBooks = new ArrayList<Book>(); // 도서 목록들을 저장할 리스트
    public BookRepositoryImpl() {
        // 샘플 도서 데이터 추가
        Book book1 = new Book();
        book1.setBookId("B001");
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
        book2.setBookId("B002");
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
        book3.setBookId("ISBN1236");
        book3.setName ("안드로이드 프로그래밍");
        book3.setUnitPrice(new BigDecimal(36000));
        book3.setAuthor("송미영");
        book3.setDescription(
                "안드로이드의 기본 개념을 체계적으로 익히고, 이를 실습 예제를 통해 익힙니다. 기본 개념과 사용법을 스스로 실전에 적용하는 방법을 학습한 다음 실습 예제와 응용 예제를 통해 실전 프로젝트 응용력을 키웁니다.");
        book3.setPublisher("길벗");
        book3.setCategory("IT교육교재");
        book3.setUnitsInStock(1000);
        book3.setReleaseDate("2023/06/30");
        book3.setFileName("ISBN1236.jpg");

        // 리스트에 도서 추가
        listOfBooks.add(book1);
        listOfBooks.add(book2);
        listOfBooks.add(book3);
    }
    @Override
    public List<Book> getAllBookList() {
//		String sql ="select * from book";
//		List<Book> listOfBooks = jdbcTemplate.query(sql, new BookRowMapper());

        String sql = "SELECT * FROM book";
        List<Book> listOfBooks = new ArrayList<>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            Book book = new Book();
            book.setBookId((String)row.get("book_id"));
            book.setName((String)row.get("name"));

            System.out.println("컬럼 목록: " + row.keySet());
//            System.out.println("null인가? 0" + row.get("unit_price"));
//            book.setUnitPrice(new BigDecimal((Integer)row.get("unit_price")));

            Object priceObj = row.get("unit_price");
            BigDecimal price = BigDecimal.ZERO;

            if (priceObj instanceof BigDecimal) {
                price = (BigDecimal) priceObj; // 바로 넣으면 됨 ✅
            } else if (priceObj instanceof Number) {
                price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
            } else if (priceObj != null) {
                try {
                    price = new BigDecimal(priceObj.toString());
                } catch (NumberFormatException e) {
                    price = BigDecimal.ZERO;
                }
            }

            book.setUnitPrice(price);


            book.setAuthor((String)row.get("author"));
            book.setDescription((String)row.get("description"));
            book.setPublisher((String)row.get("publisher"));
            book.setCategory((String)row.get("category"));

            Number units = (Number) row.get("units_in_stock");
            System.out.println("null인가? 2" + units);
            book.setUnitsInStock(units.longValue());

            book.setReleaseDate((String)row.get("release_date"));
            book.setBook_condition((String)row.get("condition"));
            book.setFileName((String)row.get("file_name"));
            listOfBooks.add(book);
        }

		
        return listOfBooks;
    }
    @Override
    public Book getBookById(String bookId) {
        Book bookInfo = null;
//        for (int i = 0; i < listOfBooks.size(); i++) {
//            Book book = listOfBooks.get(i);
//            if (book != null && book.getBookId() != null && book.getBookId().equals((bookId))) {
//                bookInfo = book;
//                break;
//            }
//        }
//        if (bookInfo == null)
//            throw new IllegalArgumentException("도서 id가 " + bookId + "인 해당 도서를 찾을 수 없습니다.");
//
        String sql = "SELECT count(*) FROM book where book_id=?";
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
        if (rowCount != 0) {
            sql = "SELECT * FROM book where book_id=?";
            bookInfo = jdbcTemplate.queryForObject(sql, new BookRowMapper(),bookId);
        }


        if (bookInfo == null) {
            throw new BookIdException(bookId);
        }
        return bookInfo;
    }
    @Override
    public List<Book> getBookListByCategory (String category){
        List<Book> booksByCategory = new ArrayList<Book>();
//        for (int i = 0; i < listOfBooks.size(); i++) {
//            Book book = listOfBooks.get(i);
//            if (category.equalsIgnoreCase(book.getCategory()))
//                booksByCategory.add(book);
//        }
        String sql = "SELECT * FROM book where category LIKE ?";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, "%" + category + "%");
//        booksByCategory = jdbcTemplate.query(sql, new BookRowMapper());
        for (Map<String, Object> row : rows) {
            Book book = new Book();
            book.setBookId((String)row.get("book_id"));
            book.setName((String)row.get("name"));

//            book.setUnitPrice(new BigDecimal((Integer)row.get("unit_price")));
            Object priceObj = row.get("unit_price");
            BigDecimal price = BigDecimal.ZERO;
            if(priceObj instanceof  BigDecimal) {
                price = (BigDecimal)  priceObj;
             } else if (priceObj instanceof  Number) {
                price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
            } else if (priceObj != null) {
                try {
                    price = new BigDecimal(priceObj.toString());
                } catch (NumberFormatException e) {
                    price = BigDecimal.ZERO;
                }
            }
            book.setUnitPrice(price);

            book.setAuthor((String)row.get("author"));
            book.setDescription((String)row.get("description"));
            book.setPublisher((String)row.get("publisher"));
            book.setCategory((String)row.get("category"));

            Number units = (Number) row.get("units_in_stock");
            book.setUnitsInStock(units.longValue());

            book.setReleaseDate((String)row.get("release_date"));
            book.setBook_condition((String)row.get("book_condition"));
            book.setFileName((String)row.get("file_name"));
            booksByCategory.add(book);
        }


        return booksByCategory;
    }
    @Override
    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        Set<Book> booksByPublisher = new HashSet<Book>();
        Set<Book> booksByCategory = new HashSet<Book>();
        Set<String> booksByFilter = filter.keySet();
        if(booksByFilter.contains("publisher")){
            for(int j=0; j<filter.get("publisher").size();j++) {
                String publisherName = filter.get("publisher").get(j);


//                for(int i = 0 ; i < listOfBooks.size(); i++){
//                    Book book =listOfBooks.get(i);
//                    if(publisherName.equalsIgnoreCase(book.getPublisher()))
//                        booksByPublisher.add(book);
//                }
                String sql = "SELECT * FROM book where publisher LIKE %" +  publisherName + "%";
                List<Book> book = jdbcTemplate.query(sql, BeanPropertyRowMapper.
                        newInstance(Book.class));
                booksByPublisher.addAll(book);

            }
        }


        if(booksByFilter.contains("category")){
            for(int i = 0; i<filter.get("category").size(); i++) {
                String category = filter.get("category").get(i);

                String sql = "SELECT * FROM book where category LIKE ?";


                List<Book> list =  jdbcTemplate.query(sql, BeanPropertyRowMapper.
                        newInstance(Book.class), "%" + category + "%");
                booksByCategory.addAll(list);
             }
        }
        booksByCategory.retainAll(booksByPublisher);
        return  booksByCategory;
     }

    @Override
    public void setNewBook(Book book){
        String checkSql ="select count(*) from book where book_id=?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, book.getBookId());
        if(count >0 ) {
            throw new BookIdException(book.getBookId());
        }


        String sql = "INSERT INTO book (book_id, name, unit_price, author, description, publisher, category, units_in_stock, release_date, book_condition, file_name)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, book.getBookId(), book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(),book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getBook_condition(), book.getFileName());


    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public  void setUpdateBook(Book book) {
        if (book.getFileName() != null) {
            String SQL = "update book set name= ?, unit_price=?, author=?, description =? , publisher =?, category=?, units_in_stock =?, release_date, book_condition=?, file_name=? where book_id=? ";
            jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.
                            getAuthor(), book.getDescription(), book.getPublisher(),
                    book.getCategory(), book.getUnitsInStock(), book.
                            getReleaseDate(), book.getBook_condition(), book.getFileName(),
                    book.getBookId());
        } else if (book.getFileName() == null) {
            String SQL = "UPDATE book set name=?, unit_price=?, author=? , description=? , publisher=?, category=?, units_in_stock=?, release_date=?, book_condition=? where book_id=?";
            jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.
                            getAuthor(), book.getDescription(), book.getPublisher(),
                    book.getCategory(), book.getUnitsInStock(), book.
                            getReleaseDate(), book.getBook_condition(), book.getBookId());
        }
    }
    @Override
    public void setDeleteBook(String bookID) {
            String sql = "delete from book where book_id =? ";
            jdbcTemplate.update(sql, bookID);
        }


    }


