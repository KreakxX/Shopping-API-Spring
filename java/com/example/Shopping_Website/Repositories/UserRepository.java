package com.example.Shopping_Website.Repositories;

import com.example.Shopping_Website.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<List<User>>findAllById(Integer id);

    Optional <User> findByEmail(String email);

    Optional<List<User>> findAllByEmail(String email);

    void deleteAllById(Integer id);
}
