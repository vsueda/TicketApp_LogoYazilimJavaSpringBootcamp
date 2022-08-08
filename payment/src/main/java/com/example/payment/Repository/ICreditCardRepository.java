package com.example.payment.Repository;

import com.example.payment.Model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICreditCardRepository extends JpaRepository<CreditCard,Long> {
}
