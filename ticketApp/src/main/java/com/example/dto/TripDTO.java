package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {

    @Size(max = 50, message = "City name length cannot more 50")
    private String tripFrom;

    @Size(max = 50, message = "City name length cannot more 50")
    private String tripTo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please enter departure time of the trip")
    private LocalDateTime timeTripFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please enter arrival time of the trip")
    private LocalDateTime timeTripTo;

    private Long busId;

    private Long planeId;


}
