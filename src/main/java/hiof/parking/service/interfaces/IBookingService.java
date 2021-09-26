package hiof.parking.service.interfaces;

import hiof.parking.model.Booking;
import hiof.parking.model.Parkingspot;
import hiof.parking.model.TYPE;

import java.io.File;
import java.text.ParseException;
import java.util.List;

public interface IBookingService {
    Booking getBookingById(long bookingid);

    List<Booking> getAllBookings();

    List<Booking> getAllBookingsOfAUser(String username);

    List<Booking> getAllBookingsOfOwnedSpots(String username);

    Booking book(long spotId, long parkinglotId, String bkdate, int hours, String username) throws Exception;

    void writeBookingsToCsv(File out) throws Exception;

    List<Parkingspot> getOnlyAvailableParkingspotsInAParkinglot(long parkinglotid, String dateAndTime, int hours, TYPE type) throws Exception;
}
