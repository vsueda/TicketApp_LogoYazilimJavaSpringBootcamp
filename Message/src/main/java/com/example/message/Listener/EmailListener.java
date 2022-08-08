package com.example.message.Listener;

import com.example.email.Model.EMail;
import com.example.message.Repository.EMailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailListener {

    @Autowired
    EMailRepository ieMailRepository;

    @RabbitListener(queues = "ticketapp.email")
    public void emailListener(EMail email) {
        log.info("email address: {}", email.getMail());
        ieMailRepository.save(email);
    }

}