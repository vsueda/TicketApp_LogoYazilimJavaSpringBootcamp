package com.example.payment.Controller;

import com.example.payment.Model.CreditCard;
import com.example.payment.Model.EFTorTransfer;
import com.example.payment.Service.ICreditCardService;
import com.example.payment.Service.IEFTorTransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/pay")
public class payTicketController {

    private ICreditCardService  iCreditCardService;

    private IEFTorTransferService iefTorTransferService;

    @PostMapping
    public ResponseEntity<Map<String, String>> payTicketbyCreditCard(@RequestBody CreditCard creditCard) {
        iCreditCardService.payTicket(creditCard);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Transaction successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> payTicketbyEftorTransfer(@RequestBody EFTorTransfer efTorTransfer) {
        iefTorTransferService.payTicket(efTorTransfer);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Transaction successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
