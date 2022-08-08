package com.example.message.Repository;

import com.example.message.Model.SMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSRepository extends JpaRepository<SMS,Long> {
}
