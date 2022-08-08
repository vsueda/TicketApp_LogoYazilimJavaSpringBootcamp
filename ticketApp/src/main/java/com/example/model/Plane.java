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
@Table(name = "tbl_plane")
public class Plane extends Vehicle{

    @Column(length = 20, nullable = false)
    private String planeNo;

    @Column(nullable = false)
    private Boolean isTransfer;

    private Integer capasity=189;

    @OneToOne
    @JoinColumn(name = "tripPlane_id")
    private Trip tripPlane;
}
