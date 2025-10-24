package com.springboot.repository;

import com.springboot.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getString("book_id"));
        book.setName(rs.getString("name"));
        book.setUnitPrice(rs.getBigDecimal("unit_price"));
        book.setAuthor(rs.getString("author"));
        book.setDescription(rs.getString("description"));
        book.setPublisher(rs.getString("publisher"));
        book.setCategory(rs.getString("category"));
        book.setUnitsInStock(rs.getLong("units_in_stock"));
        book.setReleaseDate(rs.getString("release_date"));
        book.setBook_condition(rs.getString("book_condition"));
        book.setFileName(rs.getString("file_name"));
        return book;
    }
}
