package com.example.message.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EMailRepository extends JpaRepository<com.example.email.Model.EMail,Long> {
}
