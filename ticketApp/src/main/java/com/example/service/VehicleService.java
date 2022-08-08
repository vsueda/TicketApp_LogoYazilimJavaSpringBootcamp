package com.example.service;

import com.example.dto.mapper.BusMapper;
import com.example.dto.mapper.PlaneMapper;
import com.example.dto.request.BusRequest;
import com.example.dto.request.PlaneRequest;
import com.example.model.Bus;
import com.example.model.Plane;
import com.example.repository.BusRepository;
import com.example.repository.PlaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleService {

    private PlaneRepository planeRepository;
    private BusRepository busRepository;
    private PlaneMapper planeMapper;
    private BusMapper busMapper;


    /**
     * Uçak create edildi
     * @param planeRequest planeRequest, plane create işlemi için özelleştirilmiş bir Plane dto'dur.
     */
    public void createPlane(PlaneRequest planeRequest){
        Plane plane = planeMapper.planeToPlaneRequest(planeRequest);

        planeRepository.save(plane);
    }


    /**
     *
     * @param busRequest
     */
    public void createBus(BusRequest busRequest){
        Bus bus = busMapper.busRequestToBus(busRequest);

        busRepository.save(bus);
    }
}
