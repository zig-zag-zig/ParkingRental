package hiof.parking.service;
import hiof.parking.model.*;
import hiof.parking.repository.ParkinglotRepo;
import hiof.parking.repository.UserRepo;
import hiof.parking.service.interfaces.IParkinglotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;
import static hiof.parking.helpers.LotAndSpotHelper.createScheduleForNewSpot;

@Service
public class ParkinglotService implements IParkinglotService {
    private ParkinglotRepo parkinglotRepo;
    private UserRepo userRepo;

    @Autowired
    public ParkinglotService(ParkinglotRepo parkinglotRepo, UserRepo userRepo) {
        this.parkinglotRepo = parkinglotRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Parkinglot createParkingLot(Location location, String username) {
        var user =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Parkinglot lot = new Parkinglot(location, user);
        var createdLot = parkinglotRepo.save(lot);
        return createdLot;
    }

    @Override
    public Parkinglot getParkinglotById(long parkinglotid) {
        return parkinglotRepo.findById(parkinglotid).orElseThrow(() -> new IllegalArgumentException("Parkinglot not found"));
    }

    @Override
    public List<Parkinglot> getAllParkinglots() {
        var all = parkinglotRepo.findAll();

        if (all.size() == 0)
            throw new IllegalArgumentException("None found!");

        return all;
    }

    @Override
    public void updateParkinglot(long parkinglotid, String city, String address, int number, int zipcode, String area, String username) {
        var lot = parkinglotRepo.getById(parkinglotid);

        lot.getLocation().setCity(city);
        lot.getLocation().setAddress(address);
        lot.getLocation().setNumber(number);
        lot.getLocation().setZipcode(zipcode);
        lot.getLocation().setArea(area);

        var owner =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        lot.setOwner(owner);

        parkinglotRepo.save(lot);
    }

    @Override
    public void expandScheduleOfParkingspots(long lotId, long expandByHours) throws Exception {
        var parkinglot = parkinglotRepo.findById(lotId).orElseThrow(() -> new IllegalArgumentException("Parkinglot not found"));
        var firstSpot = parkinglot.getSpots().get(0);
        var lastDateAndHourOfTheSchedule = dateFormat.parse(firstSpot.getSchedule().lastKey());

        createScheduleForNewSpot(parkinglot, firstSpot, lastDateAndHourOfTheSchedule);
    }
}
