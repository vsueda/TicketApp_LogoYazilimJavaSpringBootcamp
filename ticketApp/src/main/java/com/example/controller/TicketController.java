package com.example.controller;

import com.example.dto.request.TicketRequest;
import com.example.dto.request.TripRequest;
import com.example.dto.response.ODResponse;
import com.example.dto.response.ResponseMessage;
import com.example.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    /**
     *
     * @param ticketRequest
     * @param tripId
     * @param userId
     * @return
     */
    @PostMapping("/{tripId}/{userId}")
    @PreAuthorize(("hasRole('INDIVIDUAL') or hasRole('CORPORATE')"))
    public ResponseEntity<ODResponse> buyTicket(@Valid @RequestBody TicketRequest ticketRequest,
                                                @PathVariable("tripId") Long tripId, @PathVariable("userId") Long userId){
        ticketService.createTicket(ticketRequest,tripId,userId);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.TICKET_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/sale/{tripId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<Integer> getTotalTicketSales(@PathVariable Long tripId){
        Integer ticketSales = ticketService.totalTicketSales(tripId);

        return ResponseEntity.ok(ticketSales);
    }

    @GetMapping("/Revenue/{tripId}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<Long> getTotalTicketticketRevenue(@PathVariable Long tripId){
        Long ticketRevenue = ticketService.totalTicketRevenue(tripId);

        return ResponseEntity.ok(ticketRevenue);
    }
}