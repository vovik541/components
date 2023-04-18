package com.library.demo.mapper;

import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.service.LibrarianService;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses= LibrarianService.class)
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);


}
