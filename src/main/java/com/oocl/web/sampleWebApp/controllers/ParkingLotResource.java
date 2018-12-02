package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll(){
        ParkingLotResponse[] lots = parkingLotRepository.findAll().stream()
                .map(parkingLot -> ParkingLotResponse.create(parkingLot.getParkingLotId(),parkingLot.getCapacity())).toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(lots);
    }

}
