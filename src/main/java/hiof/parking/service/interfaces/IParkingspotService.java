package hiof.parking.service.interfaces;

import hiof.parking.model.Parkingspot;
import hiof.parking.model.TYPE;

import java.util.List;

public interface IParkingspotService {
    Parkingspot createParkingspot(long lotId, TYPE type, int hourlyPrice);

    Parkingspot getParkingspotById(long parkingspotid);

    List<Parkingspot> getAll();

    void updateParkingspot(long spotid, TYPE type, int hourlyPrice);
}
