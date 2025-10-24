package com.springboot.exception;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class BookIdException  extends  RuntimeException {
    private String bookId;
    public BookIdException(String bookId) {


        super("이미 존재하는 도서 id 입니다." + bookId);
    }

}
