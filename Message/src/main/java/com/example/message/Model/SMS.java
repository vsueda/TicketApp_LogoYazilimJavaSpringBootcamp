package com.example.message.Model;

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
@Table(name = "tbL_sms")
public class SMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsId;

    @Column(length = 10, nullable = false)
    private String toPhone;

    @Column(length = 200, nullable = false)
    private String message;

    public SMS(String toPhone, String message) {
        this.toPhone = toPhone;
        this.message = message;
    }
}
