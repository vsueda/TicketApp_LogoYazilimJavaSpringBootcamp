package com.example.dto.mapper;

import com.example.dto.request.BusRequest;
import com.example.model.Bus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusMapper {

    Bus busRequestToBus(BusRequest busRequest);
}
