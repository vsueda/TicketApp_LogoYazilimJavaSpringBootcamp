package com.example.dto.mapper;

import com.example.dto.UserDTO;
import com.example.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    List<UserDTO> map(List<User> user);

}
