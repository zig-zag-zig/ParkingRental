package hiof.parking.service.interfaces;

import hiof.parking.model.Location;
import hiof.parking.model.Parkinglot;

import java.util.List;

public interface IParkinglotService {
    Parkinglot createParkingLot(Location location, String username);

    Parkinglot getParkinglotById(long parkinglotid);

    List<Parkinglot> getAllParkinglots();

    List<Parkinglot> getAllParkinglotsOfAUser(String username);

    Parkinglot updateParkinglot(long parkinglotid, String city, String address, int number, int zipcode, String area, String username);

    void expandScheduleOfParkingspots(long lotId, int daysToExpandBy) throws Exception;
}
