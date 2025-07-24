package com.example.homework.mapper;

import com.example.homework.dto.BookResponseDTO;
import com.example.homework.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponseDTO toDto(Book book);
}
