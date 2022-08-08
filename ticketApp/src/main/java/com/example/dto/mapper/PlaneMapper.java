package com.example.dto.mapper;

import com.example.dto.request.PlaneRequest;
import com.example.model.Plane;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlaneMapper {

    Plane planeToPlaneRequest(PlaneRequest planeRequest);
}
