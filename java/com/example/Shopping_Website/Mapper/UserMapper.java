package com.example.Shopping_Website.Mapper;

import com.example.Shopping_Website.DTO.UserDTO;
import com.example.Shopping_Website.Models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserDTO dto){
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        return user;
    }
    public UserDTO toDto(User user){
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }
}
