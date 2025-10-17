package com.lms.backend.controllers;

import com.lms.backend.dtos.ApiBaseResponse;
import com.lms.backend.dtos.BookDTO;
import com.lms.backend.interfaces.BookService;
import com.lms.backend.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService service;


    @PostMapping("/book")
    public ResponseEntity<ApiBaseResponse<String>> addBook(@RequestBody BookDTO request){
        var response = service.saveBook(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ApiBaseResponse<String>> updateBookById(@PathVariable Long id, @RequestBody BookDTO request) {
        var response = service.updateBook(id, request);
        if(response.getResponseCode().equals("00")){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            var response = service.getAllBooks();
            if (response.getResponseCode().equals("99")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response.getResponseData(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            var response = service.getBook(id);
            if (response.getResponseCode().equals("99")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response.getResponseData(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ApiBaseResponse<String>> deleteBookById(@PathVariable Long id) {
        var response = service.deleteBook(id);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
