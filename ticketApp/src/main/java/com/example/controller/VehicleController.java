package com.example.controller;

import com.example.dto.request.BusRequest;
import com.example.dto.request.PlaneRequest;
import com.example.dto.response.ODResponse;
import com.example.dto.response.ResponseMessage;
import com.example.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicle")
@AllArgsConstructor
public class VehicleController {

    private VehicleService vehicleService;


    /**
     * Yeni bir uçak db'ye eklendi
     * @param planeRequest Admin kullanıcıdan alınan request formatım
     * @return Response message döner
     */
    @PostMapping("/plane")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ODResponse> createPlane(@Valid @RequestBody PlaneRequest planeRequest){

        vehicleService.createPlane(planeRequest);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.PLANE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     * Yeni bir otobüs db'ye eklendi
     * @param busRequest Otobüs eklemek için oluşturulan bir dto
     * @return Response message döner
     */
    @PostMapping("/bus")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ODResponse> createBus(@Valid @RequestBody BusRequest busRequest){

        vehicleService.createBus(busRequest);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.BUS_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
