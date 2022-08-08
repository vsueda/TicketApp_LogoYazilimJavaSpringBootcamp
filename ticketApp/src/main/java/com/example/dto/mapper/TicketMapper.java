package com.example.dto.mapper;

import com.example.dto.request.TicketRequest;
import com.example.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket ticketRequestToTicket (TicketRequest ticketRequest);
}