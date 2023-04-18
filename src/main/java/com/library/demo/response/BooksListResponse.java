package com.library.demo.response;

import com.library.demo.entity.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BooksListResponse {

    private List<BookDTO> books;


}
