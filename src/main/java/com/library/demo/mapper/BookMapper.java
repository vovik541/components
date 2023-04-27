package com.library.demo.mapper;

import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.service.LibrarianService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);

    Book bookDTOToBook(BookDTO bookDTO);

    List<BookDTO> booksToBookDTOs(List<Book> books);

}
