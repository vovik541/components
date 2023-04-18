package com.library.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    private Long userId;

    private String bookName;

    private String author;

    private String description;

    private boolean isBooked;
}
