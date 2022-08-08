package com.example.service;

import com.example.dto.mapper.TicketMapper;
import com.example.dto.request.TicketRequest;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.message.ErrorMessage;
import com.example.model.Ticket;
import com.example.model.Trip;
import com.example.model.User;
import com.example.model.enums.RoleType;
import com.example.repository.TicketRepository;
import com.example.repository.TripRepository;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;

    private TripRepository tripRepository;

    private UserRepository userRepository;

    private TicketMapper ticketMapper;


    /**
     *
     * @param ticketRequest
     * @param tripId
     * @param userId
     */
    public void createTicket(TicketRequest ticketRequest, Long tripId, Long userId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.TRIP_NOT_FOUND_MESSAGE,tripId)));

        User user = userRepository.findById(userId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.USERNAME_NOT_FOUND_MESSAGE,userId)));


        if(user.getRoles().contains(RoleType.ROLE_INDIVIDUAL)){
            List<Ticket> boughtTicketForMan = ticketRepository.findTicketByUserForTicketsAndTripForTicketsAndGender(user, trip, ticketRequest.getGender());
            List<Ticket> boughtTicket = ticketRepository.findTicketByUserForTicketsAndTripForTickets(user, trip);
            if(boughtTicket.size()>4){
                //exception
            }
            else if(boughtTicketForMan.size()>1){
                //exception
            }
        }
        if(user.getRoles().equals(RoleType.ROLE_CORPORATE)){
            List<Ticket> boughtTicket = ticketRepository.findTicketByUserForTicketsAndTripForTickets(user, trip);
            if(boughtTicket.size()>19){
                //exception
            }
        }
        Ticket ticket = ticketMapper.ticketRequestToTicket(ticketRequest);
        ticket.setUserForTickets(user);
        ticket.setTripForTickets(trip);
        ticketRepository.save(ticket);

    }

    public Integer totalTicketSales(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.TRIP_NOT_FOUND_MESSAGE,tripId)));

        return ticketRepository.findTicketByTripForTickets(trip).size();
    }

    public Long totalTicketRevenue(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.TRIP_NOT_FOUND_MESSAGE,tripId)));


       return ticketRepository.findTicketByTripForTickets(trip).size()*trip.getTripPrice();

    }

}