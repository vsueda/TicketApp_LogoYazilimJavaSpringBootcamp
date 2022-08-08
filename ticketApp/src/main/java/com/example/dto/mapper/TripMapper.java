package com.example.dto.mapper;

import com.example.dto.TripDTO;
import com.example.dto.request.TripRequest;
import com.example.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripMapper {

    Trip tripRequestToTrip(TripRequest tripRequest);

    TripDTO tripDTOToTrip(Trip trip);


}
