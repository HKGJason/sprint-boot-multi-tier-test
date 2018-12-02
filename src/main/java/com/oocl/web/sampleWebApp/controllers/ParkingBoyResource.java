package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @GetMapping
    public ResponseEntity<ParkingBoyResponse[]> getAll() {
        final ParkingBoyResponse[] parkingBoys = parkingBoyRepository.findAll().stream()
            .map(ParkingBoyResponse::create)
            .toArray(ParkingBoyResponse[]::new);
        return ResponseEntity.ok(parkingBoys);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addNewParkingBoy(@RequestBody ParkingBoy boy){
        if (!checkEmployeeIdUnique(boy.getEmployeeId()))
        {
            return ResponseEntity.badRequest().build();
        }
        if (parkingBoyRepository.save(boy) == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(URI.create("/parkingboys/"+boy.getEmployeeId())).build();

    }

    private boolean checkEmployeeIdUnique(String id) {
        List<ParkingBoy> boys =  parkingBoyRepository.findAll();
        for (ParkingBoy boy: boys){
            boy.getEmployeeId().equals(id);
            return false;
        }
        return true;
    }
}
