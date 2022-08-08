package com.example.email.Model;

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
@Table(name = "tbL_email")
public class EMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailId;

    @Column(length = 100, nullable = false)
    private String to;

    @Column(length = 200)
    private String title;

    @Column(length = 200, nullable = false)
    private String mail;


    public EMail(String to, String title, String mail) {
        this.to = to;
        this.title = title;
        this.mail = mail;
    }
}
