package com.example.service;

import com.example.dto.TripDTO;
import com.example.dto.mapper.TripMapper;
import com.example.dto.request.TripRequest;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.message.ErrorMessage;
import com.example.model.Bus;
import com.example.model.Trip;
import com.example.model.Vehicle;
import com.example.repository.BusRepository;
import com.example.repository.PlaneRepository;
import com.example.repository.TripRepository;
import com.example.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TripService {

    private TripRepository tripRepository;
    private BusRepository busRepository;
    private PlaneRepository planeRepository;
    private TripMapper tripMapper;


    /**
     * Otobüs seyehati olusturuldu
     * @param tripRequest bir Trip dto'dur
     * @param busId endpointte gelen seyehatin yapılacağı bus id
     */
    public void createTripForBus(TripRequest tripRequest, Long busId){
        checkTripTimeIsCorrect(tripRequest.getTimeTripFrom(), tripRequest.getTimeTripTo());

        busRepository.findById(busId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.BUS_NOT_FOUND_MESSAGE,busId)));

        Trip trip = tripMapper.tripRequestToTrip(tripRequest);
        trip.setBusId(busId);

        tripRepository.save(trip);
    }

    /**
     * Uçak seyehati olusturuldu
     * @param tripRequest bir Trip dto'dur
     * @param planeId endpointte gelen seyehatin yapılacağı plane id
     */
    public void createTripForPlane(TripRequest tripRequest, Long planeId){
        checkTripTimeIsCorrect(tripRequest.getTimeTripFrom(), tripRequest.getTimeTripTo());

        planeRepository.findById(planeId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.PLANE_NOT_FOUND_MESSAGE,planeId)));

        Trip trip = tripMapper.tripRequestToTrip(tripRequest);
        trip.setPlaneId(planeId);

        tripRepository.save(trip);
    }

    /**
     * seyahat silen method
     * @param tripId seyahat id'si
     */
    public  void deleteTrip(Long tripId){
         Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new
                 ResourceNotFoundException(String.format(ErrorMessage.TRIP_NOT_FOUND_MESSAGE,tripId)));

         tripRepository.delete(trip);
    }

    /**
     * Seyahatler için spesifik arama methodu eklendi
     * @param tripDTO
     * @return Arama kriterlerine uyan Tripleri List olarak döner.
     */
    public List<Trip> searchTripByParameters(TripDTO tripDTO){
        return tripRepository.findTripByTripFromOrTripToOrTimeTripFromOrTimeTripToOrBusIdOrPlaneId(
                tripDTO.getTripFrom(),tripDTO.getTripTo(),tripDTO.getTimeTripFrom(),tripDTO.getTimeTripTo(),tripDTO.getBusId(),tripDTO.getPlaneId());
    }

    /**
     * Admin kullanıcının kalkış ve varış saatlerini geçmiş zamanda veya tutarsız bir şekilde yazmasını önleyen kontrol methodu
     * @param timeTripFrom kalkış saati
     * @param timeTripTo varış saati
     */
    private void checkTripTimeIsCorrect(LocalDateTime timeTripFrom, LocalDateTime timeTripTo){
        LocalDateTime now= LocalDateTime.now();

        if (timeTripFrom.isBefore(now)){
            throw new BadRequestException(ErrorMessage.TRIP_TIME_INCORRECT_MESSAGE);
        }

        boolean isEqual= timeTripFrom.isEqual(timeTripTo)?true:false;
        boolean isBefore=timeTripFrom.isBefore(timeTripTo)?true:false;

        if(isEqual||!isBefore) {
            throw new BadRequestException(ErrorMessage.TRIP_TIME_INCORRECT_MESSAGE);
        }
    }
}
