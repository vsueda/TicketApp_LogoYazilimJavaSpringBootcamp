package com.example.controller;

import com.example.dto.TripDTO;
import com.example.dto.request.TripRequest;
import com.example.dto.response.ODResponse;
import com.example.dto.response.ResponseMessage;
import com.example.model.Trip;
import com.example.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trip")
public class TripController {

    private TripService tripService;


    /**
     * Admin kullanıcı otobüs seyahati oluşturur.
     * @param tripRequest bir Trip dto'dur
     * @param busId endpointte id'si gelen seyahatin yapılacağı otobüs id'si
     * @return Response message döner
     */
    @PostMapping("/bus/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ODResponse> createTripForBus(@Valid @RequestBody TripRequest tripRequest,
                                                       @PathVariable ("id") Long busId){
        tripService.createTripForBus(tripRequest, busId);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.TRIP_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Admin kullanıcı uçak seyahati oluşturur.
     * @param tripRequest bir Trip dto'dur
     * @param planeId endpointte id'si gelen seyahatin yapılacağı uçak id'si
     * @return Response message döner
     */
    @PostMapping("/plane/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ODResponse> createTripForPlane(@Valid @RequestBody TripRequest tripRequest,
                                                         @PathVariable ("id") Long planeId){
        tripService.createTripForPlane(tripRequest, planeId);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.TRIP_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Admin kullanıcı seyahat silebilir
     * @param tripId endpointte gelen ve silinmek istenen trip id'si
     * @return Response message döner
     */
    @DeleteMapping("/cancel/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<ODResponse> deleteTrip(@PathVariable ("id") Long tripId){
        tripService.deleteTrip(tripId);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.TRIP_DELETED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Kullanicilarin spesifik olarak sorguladıkları Tripleri getiren method
     * @param tripDTO Kullanıcıdan aldığım Trip bilgirini düzenlediğim dto
     * @return Sorgulanan Tripler döner.
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INDIVIDUAL') or hasRole('CORPORATE')")
    public ResponseEntity<List<Trip>> getTrip(@RequestBody TripDTO tripDTO){
        List<Trip> trip = tripService.searchTripByParameters(tripDTO);
        return ResponseEntity.ok(trip);
    }


}
