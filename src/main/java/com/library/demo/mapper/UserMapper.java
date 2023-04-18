package com.library.demo.mapper;


import com.library.demo.entity.User;
import com.library.demo.entity.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToUserDTO(User user);
}
