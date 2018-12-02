package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll(){
        ParkingLotResponse[] lots = parkingLotRepository.findAll().stream()
                .map(parkingLot -> ParkingLotResponse.create(parkingLot.getParkingLotId(),parkingLot.getCapacity(), parkingLot.getAvailablePositionCount())).toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(lots);
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addNewLot(@RequestBody ParkingLot lot)
    {
        if(parkingLotRepository.save(lot)==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("/parkinglots/"+lot.getParkingLotId())).build();
    }
}
