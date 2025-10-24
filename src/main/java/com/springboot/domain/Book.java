package com.springboot.domain;

import com.springboot.validator.BookId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Entity
public class Book {

    @Id
    @Pattern(regexp="ISBN[1-9]+", message = "{Pattern.book.bookId}")
    private String bookId;

    @Size(min=4, max=50, message = "{Size.book.name}")
    private String name;

    @Min(value=0, message="{Min.book.unitPrice}")
    @Digits(integer=8, fraction=2, message="{Digits.book.unitPrice}")
    @NotNull(message = "{NotNull.book.unitPrice}")
    private BigDecimal unitPrice;

    private String author;
    private String description;
     private String publisher;
    private String category;
    private long unitsInStock;
    private String releaseDate;
    private String book_condition;
    private String fileName;
    @Transient
    private MultipartFile bookImage;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(long unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }





    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Book() {
    }
}
