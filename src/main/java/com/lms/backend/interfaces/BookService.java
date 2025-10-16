package com.lms.backend.interfaces;

import com.lms.backend.dtos.ApiBaseResponse;
import com.lms.backend.dtos.BookDTO;
import com.lms.backend.models.Book;

import java.util.List;

public interface BookService {

    ApiBaseResponse<String> saveBook(BookDTO bookDTO);
    ApiBaseResponse<String> updateBook(Long id, BookDTO request);
    ApiBaseResponse<String> deleteBook(Long id);
    ApiBaseResponse<Book> getBook(Long id);
    ApiBaseResponse<List<Book>> getAllBooks();
}
