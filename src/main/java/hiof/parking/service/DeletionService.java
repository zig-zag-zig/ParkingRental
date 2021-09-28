package hiof.parking.service;
import hiof.parking.model.Booking;
import hiof.parking.model.Parkingspot;
import hiof.parking.model.User;
import hiof.parking.repository.BookingRepo;
import hiof.parking.repository.ParkinglotRepo;
import hiof.parking.repository.ParkingspotRepo;
import hiof.parking.repository.UserRepo;
import hiof.parking.service.interfaces.IDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class DeletionService implements IDeletionService {
    private BookingRepo bookingRepo;
    private UserRepo userRepo;
    private ParkinglotRepo parkinglotRepo;
    private ParkingspotRepo parkingspotRepo;

    @Autowired
    public DeletionService(BookingRepo bookingRepo, UserRepo userRepo, ParkinglotRepo parkinglotRepo, ParkingspotRepo parkingspotRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.parkinglotRepo = parkinglotRepo;
        this.parkingspotRepo = parkingspotRepo;
    }

    @Override
    public boolean deleteUser(String username) {
        var user =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        deleteBookingsOfDeletedUser(user);

        //method to delete the parkinglots the user owns
        deleteOwnedParkinglotsOfUserToBeDeleted(user);

        //remove the user from the map of all users
        try {
            userRepo.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void deleteBookingsOfDeletedUser(User user) {
        var allBookings = bookingRepo.findAll();
        for (var booking : allBookings){
            //find the bookings made by the user about to be deleted
            if (booking.getUser().getId() == user.getId()){
                //calling the method deleteBooking to delete the bookings made by the user
                deleteBooking(booking.getId());
            }
        }
    }

    private void deleteOwnedParkinglotsOfUserToBeDeleted(User user) {
        var allParkinglots = parkinglotRepo.findAll();

        for (var parkinglot : allParkinglots){
            if (parkinglot.getOwner().getId() == user.getId()){
                parkinglotRepo.delete(parkinglot);
            }
        }
    }

    @Override
    public boolean deleteParkingspot(long spotid) {
        var spot = parkingspotRepo.findById(spotid).orElseThrow(() -> new IllegalArgumentException("Spot not found"));

        deleteBookingsRelatedToSpotsToBeDeleted(spot.getId());
        removeParkingspotFromParkinglot(spotid, spot.getParkinglotId());

        try {
            parkingspotRepo.delete(spot);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void removeParkingspotFromParkinglot(long spotid, long lotId) {
        var parkinglot = parkinglotRepo.getById(lotId);
        parkinglot.getSpots().removeIf(ps -> ps.getId() == spotid);
        parkinglotRepo.save(parkinglot);
    }

    private void deleteBookingsRelatedToSpotsToBeDeleted(long spotid) {
        ArrayList<Long> bookingids = new ArrayList<>();
        findBookingIdOfDeletedSpot(spotid, bookingids);

        for (int i = 0; i < bookingids.size(); i++) {
            deleteBooking(bookingids.get(i));
        }
    }

    private void findBookingIdOfDeletedSpot(long spotid, ArrayList<Long> bookingids) {
        var allBookings = bookingRepo.findAll();
        for (var booking : allBookings) {
            if (booking.getSpot().getId() == spotid) {
                //put the bookingids in an arraylist called bookingids
                bookingids.add(booking.getId());
            }
        }
    }

    @Override
    public boolean deleteBooking(long bookingnumber) {
        var booking =  bookingRepo.findById(bookingnumber).orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        var spot = booking.getSpot();

        resetStatusToAvailableAfterDeletingBooking(booking, spot);

        try {
            bookingRepo.delete(booking);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void resetStatusToAvailableAfterDeletingBooking(Booking booking, Parkingspot spot) {
        var schedule = booking.getSpot().getSchedule();

        Iterator<String> iterator = schedule.keySet().iterator();
        while (iterator.hasNext()) {
            String dateAndTime = iterator.next();
            //match the bookingdate and the date inside the schedule
            if (booking.getDateAndTime().contains(dateAndTime)) {
                //set the spot that was cancelled to available so it can be booked again
                schedule.put(dateAndTime, true);
            }
        }
        parkingspotRepo.save(spot);
    }

    @Override
    public boolean deleteParkinglot(long parkinglotid) {
        var parkinglot = parkinglotRepo.findById(parkinglotid).orElseThrow(() -> new IllegalArgumentException("Parkinglot not found"));

        var spotsInLot = parkinglot.getSpots();

        var toBeDeleted = new ArrayList<Parkingspot>();

        for (var spot : spotsInLot) {
            toBeDeleted.add(spot);
            deleteBookingsRelatedToSpotsToBeDeleted(spot.getId());
        }

        try {
            parkinglotRepo.delete(parkinglot);
            parkingspotRepo.deleteAll(toBeDeleted);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
