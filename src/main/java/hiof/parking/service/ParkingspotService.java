package hiof.parking.service;

import hiof.parking.model.Parkingspot;
import hiof.parking.model.TYPE;
import hiof.parking.repository.ParkinglotRepo;
import hiof.parking.repository.ParkingspotRepo;
import hiof.parking.service.interfaces.IParkingspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static hiof.parking.helpers.LotAndSpotHelper.createScheduleForNewSpot;

@Service
public class ParkingspotService implements IParkingspotService {
    private ParkingspotRepo parkingspotRepo;
    private ParkinglotRepo parkinglotRepo;

    @Autowired
    public ParkingspotService(ParkingspotRepo parkingspotRepo, ParkinglotRepo parkinglotRepo) {
        this.parkingspotRepo = parkingspotRepo;
        this.parkinglotRepo = parkinglotRepo;
    }

    @Override
    public Parkingspot createParkingspot(long lotId, TYPE type, int hourlyPrice) {
        var spot = new Parkingspot(type, hourlyPrice);

        var lot = parkinglotRepo.getById(lotId);

        createScheduleForNewSpot(lot, spot);
        spot.setParkinglotId(lotId);
        var createdSpot = parkingspotRepo.save(spot);
        lot.getSpots().add(createdSpot);
        parkinglotRepo.save(lot);
        return createdSpot;
    }

    @Override
    public Parkingspot getParkingspotById(long parkingspotid) {
        return parkingspotRepo.findById(parkingspotid).orElseThrow(() -> new IllegalArgumentException("Parkingspot not found"));
    }

    @Override
    public Parkingspot updateParkingspot(long spotid, TYPE type, int hourlyPrice) {
        var spot = parkingspotRepo.findById(spotid).orElseThrow(() -> new IllegalArgumentException("Parkingspot not found"));
        spot.setType(type);
        spot.setHourlyPrice(hourlyPrice);
        return parkingspotRepo.save(spot);
    }
}