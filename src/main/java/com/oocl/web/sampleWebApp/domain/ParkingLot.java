package com.oocl.web.sampleWebApp.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "parking_lot_id", unique = true, nullable = false)
    private String parkingLotId;

    private ParkingBoy boy;

    private int availablePositionCount;

    private int capacity;

    public ParkingLot(){

    }
    public ParkingLot(String id, int capacity){
        this.parkingLotId = id;
        this.capacity = capacity;
        this.availablePositionCount = capacity;
    }

    public void setBoy(ParkingBoy boy) {
        this.boy = boy;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ParkingBoy getBoy() {
        return boy;
    }

    public int getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(int availablePositionCountl) {
        this.availablePositionCount = availablePositionCountl;
    }

    public long getId() {
        return id;
    }
}
