package com.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaneRequest {

    @Size(max = 20)
    @NotNull(message = "Please provide vehicle name")
    private String vehicleName;

    @Size(max = 50)
    @NotNull(message = "Please provide company name")
    private String companyName;

    @Size(max = 20)
    @NotNull(message = "Please provide plane no")
    private String planeNo;

    private Boolean isTransfer;

    private Integer capasity=189;
}
