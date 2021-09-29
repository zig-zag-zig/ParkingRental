package hiof.parking.service;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.Location;
import hiof.parking.model.Parkinglot;
import hiof.parking.repository.ParkinglotRepo;
import hiof.parking.repository.ParkingspotRepo;
import hiof.parking.repository.UserRepo;
import hiof.parking.service.interfaces.IParkinglotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;
import static hiof.parking.helpers.LotAndSpotHelper.*;

@Service
public class ParkinglotService implements IParkinglotService {
    private ParkinglotRepo parkinglotRepo;
    private ParkingspotRepo parkingspotRepo;
    private UserRepo userRepo;

    @Autowired
    public ParkinglotService(ParkinglotRepo parkinglotRepo, ParkingspotRepo parkingspotRepo, UserRepo userRepo) {
        this.parkinglotRepo = parkinglotRepo;
        this.parkingspotRepo = parkingspotRepo;
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
    public List<Parkinglot> getAllParkinglotsOfAUser(String username) {
        var all = getAllParkinglots();

        var allOfUser = new ArrayList<Parkinglot>();

        for (var lot : all) {
            if (lot.getOwner().getUsername().equalsIgnoreCase(username)) {
                allOfUser.add(lot);
            }
        }

        if (allOfUser.isEmpty())
            throw new IllegalArgumentException("None found!");

        return all;
    }

    @Override
    public Parkinglot updateParkinglot(long parkinglotid, String city, String address, int number, int zipcode, String area, String username) {
        var lot = parkinglotRepo.getById(parkinglotid);

        lot.getLocation().setCity(city);
        lot.getLocation().setAddress(address);
        lot.getLocation().setNumber(number);
        lot.getLocation().setZipcode(zipcode);
        lot.getLocation().setArea(area);

        var owner =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        lot.setOwner(owner);

        return parkinglotRepo.save(lot);
    }

    @Override
    public void expandScheduleOfParkingspots(long lotId, int daysToExpandBy) throws Exception {
        if (daysToExpandBy < 1)
            throw new IllegalArgumentException("Must expand by at least one day!");

        var parkinglot = parkinglotRepo.findById(lotId).orElseThrow(() -> new IllegalArgumentException("Parkinglot not found"));
        var firstSpot = parkinglot.getSpots().get(0);
        var lastDateAndHourOfTheSchedule = dateFormat.parse(firstSpot.getSchedule().lastKey());

        expandScheduleOfAllParkingspotsInParkinglot(parkinglot, lastDateAndHourOfTheSchedule, daysToExpandBy);
    }

    private void expandScheduleOfAllParkingspotsInParkinglot(Parkinglot parkinglot, Date from, int daysToExpandBy) {
        removeOldDatesFromSchedule(parkinglot);

        SortedMap<String, Boolean> newSchedule = new ConcurrentSkipListMap<>();

        long hoursToExpandBy = daysToExpandBy * 24;

        for (int hours = 1; hours <= hoursToExpandBy; hours++) {
            var dateAndTimeToAddToSchedule = DateCheckerHelper.addHoursToAnyTime(from, hours);
            newSchedule.put(DateCheckerHelper.dateFormat.format(dateAndTimeToAddToSchedule), true);
        }

        for (var spot : parkinglot.getSpots()) {
            spot.getSchedule().putAll(newSchedule);
            parkingspotRepo.save(spot);
        }
    }
}
