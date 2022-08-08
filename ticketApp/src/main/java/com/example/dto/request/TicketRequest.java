package com.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {


    @Size(max = 50, message = "Name length cannot more 50")
    @NotNull(message = "Please enter departure First Name")
    private String firstName;

    @Size(max = 50, message = "lastName length cannot more 50")
    @NotNull(message = "Please enter departure Last Name")
    private String lastName;

    @Size(max = 50, message = "email length cannot more 50")
    @NotNull(message = "Please enter departure email")
    @Email(message = "Düzgün gir!!")
    private String email;

    @Size(max = 1, message = "Please choose 'W' woman for or 'M' man for")
    @NotNull(message = "Please enter departure gender")
    private String gender;

}