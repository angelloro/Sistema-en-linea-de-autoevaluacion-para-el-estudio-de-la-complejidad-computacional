package com.vaporware.WebComplejidad;

import org.springframework.data.repository.CrudRepository;

import com.vaporware.WebComplejidad.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username=?1  ")
    User findByUserName(String username);

    @Modifying
    @Query("DELETE FROM User u where u.username=:username")
    void deleteUser(@Param("username") String username);

}
