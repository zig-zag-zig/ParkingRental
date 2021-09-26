package hiof.parking.service.interfaces;

import hiof.parking.model.*;

import java.util.List;

public interface IParkinglotService {
    Parkinglot createParkingLot(Location location, String username);

    Parkinglot getParkinglotById(long parkinglotid);

    List<Parkinglot> getAllParkinglots();

    void updateParkinglot(long parkinglotid, String city, String address, int number, int zipcode, String area, String username);

    void expandScheduleOfParkingspots(long lotId, long hoursToExpandBy) throws Exception;
}
