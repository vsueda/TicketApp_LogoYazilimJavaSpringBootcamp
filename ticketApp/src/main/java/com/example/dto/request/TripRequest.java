package com.example.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {

    @NotNull(message = "Please provide trip price")
    private Long tripPrice;

    @Size(max = 50, message = "City name length cannot more 50")
    @NotNull(message = "Please enter departure location")
    private String tripFrom;

    @Size(max = 50, message = "City name length cannot more 50")
    @NotNull(message = "Please enter destination location")
    private String tripTo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please enter departure time of the trip")
    private LocalDateTime timeTripFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please enter arrival time of the trip")
    private LocalDateTime timeTripTo;

}
