package com.example.message.Listener;

import com.example.message.Model.SMS;
import com.example.message.Repository.EMailRepository;
import com.example.message.Repository.SMSRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSListener {

    @Autowired
    SMSRepository smsRepository;

    @RabbitListener(queues = "ticketapp.sms")
    public void emailListener(SMS sms) {
        log.info("phone number: {}", sms.getToPhone());
        smsRepository.save(sms);
    }
}
