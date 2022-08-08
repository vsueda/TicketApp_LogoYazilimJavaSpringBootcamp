package com.example.payment.Service;

import com.example.payment.Model.CreditCard;
import com.example.payment.Repository.ICreditCardRepository;

public class CreditCardServiceImpl implements ICreditCardService{

    private ICreditCardRepository iCreditCardRepository;

    @Override
    public void payTicket(CreditCard creditCard) {
        iCreditCardRepository.save(creditCard);
    }
}
