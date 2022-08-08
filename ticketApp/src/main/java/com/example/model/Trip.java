package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tripPrice;

    @Column(length = 50, nullable = false)
    private String tripFrom;

    @Column(length = 50, nullable = false)
    private String tripTo;

    @Column(nullable = false)
    private LocalDateTime timeTripFrom;

    @Column(nullable = false)
    private LocalDateTime timeTripTo;

    private Long busId;

    private Long planeId;

    @OneToMany(mappedBy = "tripForTickets")
    private Set<Ticket> ticketsForTrip;

    @OneToOne(mappedBy = "tripBus")
    private Bus bus;

    @OneToOne(mappedBy = "tripPlane")
    private Plane plane;


}
