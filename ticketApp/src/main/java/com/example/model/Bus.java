package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_bus")
public class Bus extends Vehicle{

    @Column(length = 20, nullable = false)
    private String busNo;

    private Integer capasity=45;

    @OneToOne
    @JoinColumn(name = "tripBus_id")
    private Trip tripBus;
}
