package com.lms.backend.services;

import com.lms.backend.dtos.ApiBaseResponse;
import com.lms.backend.dtos.BookDTO;
import com.lms.backend.interfaces.BookService;
import com.lms.backend.models.Book;
import com.lms.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;
    private final ModelMapper mapper;
    @Override
    public ApiBaseResponse<String> saveBook(BookDTO bookDTO) {
        ApiBaseResponse<String> response = new ApiBaseResponse<>();
        Book newRecord = mapper.map(bookDTO, Book.class);
        repo.save(newRecord);

        response.setResponseCode("00");
        response.setResponseMessage("Successfully saved");
        response.setResponseData(null);
        return response;
    }

    @Override
    public  ApiBaseResponse<String> updateBook(Long id, BookDTO request) {
        var currentBook =  repo.findById(id);
        ApiBaseResponse<String> response = new ApiBaseResponse<>();
        if(currentBook.isPresent()){
            var book = currentBook.get();
            book.setAuthor(request.getAuthor());
            book.setIsbn(request.getIsbn());
            book.setTitle(request.getTitle());
            repo.save(book);

            response.setResponseCode("00");
            response.setResponseMessage("Successfully saved");
            response.setResponseData(null);
            return response;
        }
        response.setResponseCode("99");
        response.setResponseMessage("Not found");
        response.setResponseData(null);
        return response;

    }

    @Override
    public ApiBaseResponse<String> deleteBook(Long id) {
        ApiBaseResponse<String> response = new ApiBaseResponse<>();
        try{
            repo.deleteById(id);
            response.setResponseCode("00");
            response.setResponseMessage("Successfully deleted");
            response.setResponseData(null);
        }catch (Exception ex){
            response.setResponseCode("96");
            response.setResponseMessage("Id not found:" + id);
            response.setResponseData(null);
        }
        return response;
    }

    @Override
    public ApiBaseResponse<Book> getBook(Long id) {
        ApiBaseResponse<Book> response = new ApiBaseResponse<>();
        try {
            var book = repo.findById(id);
            if(book.isEmpty()){
                response.setResponseCode("99");
                response.setResponseMessage("Not found");
                response.setResponseData(null);
            }else{
                response.setResponseCode("00");
                response.setResponseMessage("Successfully retrieved");
                response.setResponseData(book.get());
            }
            return response;

        }catch (Exception ex){
            response.setResponseCode("99");
            response.setResponseMessage("An error occurred");
            response.setResponseData(null);
        }
        return response;
    }

    @Override
    public ApiBaseResponse<List<Book>> getAllBooks() {
        ApiBaseResponse<List<Book>> response = new ApiBaseResponse<>();
        try {
            List<Book> books = new ArrayList<>(repo.findAll());
            if(books.isEmpty()){
                response.setResponseCode("99");
                response.setResponseMessage("Not found");
                response.setResponseData(null);
            }else{
                response.setResponseCode("00");
                response.setResponseMessage("Successfully retrieved");
                response.setResponseData(books);
            }
            return response;

        }catch (Exception ex){
            response.setResponseCode("96");
            response.setResponseMessage("An error occurred");
            response.setResponseData(null);
        }
        return response;
    }
}
