package com.example.Shopping_Website.Service;

import com.example.Shopping_Website.DTO.UserDTO;
import com.example.Shopping_Website.Exception.UserNotFoundException;
import com.example.Shopping_Website.Mapper.UserMapper;
import com.example.Shopping_Website.Models.User;
import com.example.Shopping_Website.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public User createNewUser(User user) {
        return repository.save(user);
    }

    public UserDTO findUserById(Integer id) {
        Optional<User> OptionalUser = repository.findById(id);
        if (OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            UserDTO dto = mapper.toDto(user);
            return dto;
        } else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public List<UserDTO> findAllUsersById(Integer id) {
        Optional<List<User>> OptionalUsers = repository.findAllById(id);
        if (OptionalUsers.isPresent()) {
            List<User> users = OptionalUsers.get();
            List<UserDTO> dtos = users.stream().map(mapper::toDto).collect(Collectors.toList());
            return dtos;
        } else {
            throw new UserNotFoundException("Users with id: " + id + " not found");
        }
    }

    public UserDTO findUserByEmail(String Email) {
        Optional<User> OptionalUser = repository.findByEmail(Email);
        if (OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            UserDTO dto = mapper.toDto(user);
            return dto;
        } else {
            throw new UserNotFoundException("User with email: " + Email + " not found");
        }
    }

    public List<UserDTO> findAllUsersByEmail(String Email) {
        Optional<List<User>> OptionalUsers = repository.findAllByEmail(Email);
        if (OptionalUsers.isPresent()) {
            List<User> users = OptionalUsers.get();
            List<UserDTO> dtos = users.stream().map(mapper::toDto).collect(Collectors.toList());
            return dtos;
        } else {
            throw new UserNotFoundException("Users with email: " + Email + " not found");
        }

    }
    public User findUserByIdNoDTO(Integer id){
        Optional<User> OptionalUser = repository.findById(id);
        if(OptionalUser.isPresent()){
            User user = OptionalUser.get();
            return user;
        }else{
            throw new UserNotFoundException("User with Id: "+ id+" not found");
        }
    }

    public User updatePasswordFromUser(String email, String oldPassword, String newPassword) {
        Optional<User> OptionalUser = repository.findByEmail(email);
        if (OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                return repository.save(user);
            }else{
                throw new RuntimeException("The provided Password don't match");
            }
        }else{
            throw new UserNotFoundException("User with email: "+ email+ " not found");
        }
    }
    @Secured("ROLE_ADMIN")
    public void deleteUserById(Integer id){
        repository.deleteById(id);
    }

    public void deleteAllUserById(Integer id){
        repository.deleteAllById(id);
    }

    @Secured("ROLE_ADMIN")
    public void deleteAll(){
        repository.deleteAll();
    }


}
