package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName, u.email=:email"
            + ", u.phoneNumber=:phoneNumber WHERE u.userId=:id")
    void update(@Param("id") Long id, @Param("firstName") String firstName , @Param("lastName") String lastName,
                 @Param("email") String email, @Param("phoneNumber") String phoneNumber);
}
