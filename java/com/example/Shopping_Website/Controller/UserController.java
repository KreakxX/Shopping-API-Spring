package com.example.Shopping_Website.Controller;

import com.example.Shopping_Website.DTO.UserDTO;
import com.example.Shopping_Website.Models.User;
import com.example.Shopping_Website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    /* @PostMapping("/newUser")
    public User createNewUser(@RequestBody User user){
        return service.createNewUser(user);
    }
    */

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable Integer id){
        return service.findUserById(id);
    }

    @GetMapping("/All/{id}")
    public List<UserDTO> findUsersById(@PathVariable Integer id){
        return service.findAllUsersById(id);
    }

    @GetMapping("/{email}")
    public UserDTO findUserByEmail(String email){
        return service.findUserByEmail(email);
    }

    @GetMapping("/All/{email}")
    public List<UserDTO> findUsersByEmail(String email){
        return service.findAllUsersByEmail(email);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(Integer id){
        service.deleteUserById(id);
    }

    @DeleteMapping("/delete/All")
    public void deleteAll(){
        service.deleteAll();
    }

    @PutMapping("/new/Password/email/{email}/old-Password/{oldpassword}/new-Password/{newpassword}")
    public User updatePasswordFromUser(
            @PathVariable String email, @PathVariable String oldpassword, @PathVariable String newpassword
    ){
        return service.updatePasswordFromUser(email,oldpassword,newpassword);
    }

    @GetMapping("/{id}/NoDto")
    public User findUserByIdNoDto(@PathVariable Integer id){
        return service.findUserByIdNoDTO(id);
    }

    @GetMapping("/secured")
    public String se  (){
        return "HELLO FROM SECURED ENDPOINT";
    }
}
