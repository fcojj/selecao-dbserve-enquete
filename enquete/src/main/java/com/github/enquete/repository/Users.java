package com.github.enquete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.enquete.model.User;

public interface Users extends JpaRepository<User, Long> {
	
    @Query("SELECT user FROM User user WHERE LOWER(user.username) = LOWER(:username)")
    public List<User> find(@Param("username") String username);

}
