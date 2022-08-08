package com.example.repository;

import com.example.model.Ticket;
import com.example.model.Trip;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query ("SELECT t FROM Ticket t WHERE t.userForTickets = ?1 and t.tripForTickets = ?2")
    List<Ticket> findTicketByUserForTicketsAndTripForTickets(User userId, Trip tripId);

    @Query ("SELECT t FROM Ticket t WHERE t.userForTickets = ?1 and t.tripForTickets = ?2 and t.gender = ?3 ")
    List<Ticket> findTicketByUserForTicketsAndTripForTicketsAndGender(User userId, Trip tripId, String gender);

    @Query("SELECT t FROM Ticket t WHERE t.tripForTickets = ?1")
    List<Ticket> findTicketByTripForTickets(Trip trip);
}