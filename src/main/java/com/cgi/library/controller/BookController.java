package com.cgi.library.controller;

import com.cgi.library.entity.Book;
import com.cgi.library.model.BookDTO;
import com.cgi.library.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    //private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "getBooks")
    public ResponseEntity<Page<BookDTO>> getBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooks(pageable));
    }

    @GetMapping(value = "getBook")
    public ResponseEntity<BookDTO> getBook(@RequestParam(value = "bookId") UUID bookId) {
        return ResponseEntity.ok(bookService.getBook(bookId));
    }

    @PostMapping(value = "saveBook")
    public ResponseEntity<String> saveBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(String.valueOf(bookService.saveBook(book)));
    }

    @DeleteMapping(value = "deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "bookId") UUID bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("");
    }

    //@GetMapping("/searchBooks")
    //public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam("searchText") String searchText) {
    //    List<Book> books = bookService.searchBooks(searchText);
    //    List<BookDTO> bookDTOs = books.stream()
    //            .map(book -> modelMapper.map(book, BookDTO.class))
    //            .collect(Collectors.toList());
    //    return ResponseEntity.ok(bookDTOs);
    //}
}
