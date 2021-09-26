package hiof.parking.unit;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.*;
import hiof.parking.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.Date;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;

@Service
public class TestFactory {
    @Autowired
    public IBookingService bookingService;
    @Autowired
    public IUserService userService;
    @Autowired
    public IParkinglotService parkinglotService;
    @Autowired
    public IParkingspotService parkingspotService;
    @Autowired
    public IDeletionService deletionService;
    public User user;
    public Parkinglot parkinglot;
    public Parkingspot spot1;
    public Booking booking;
    public Date bkdatePlusThreeHours = DateCheckerHelper.addHoursToAnyTime(new Date(), 3);

    public void user() throws Exception {
        user = this.userService.createUser("ax", "a", "a", "First", "Usah", "Home", "ff", 2, 1001, "dd");
    }

    public void parkinglot() throws Exception {
        parkinglot = this.parkinglotService.createParkingLot(new Location("Testistad", "Test road", 23, 1321, "Fake"), user.getUsername());
    }

    public void parkingspot() throws Exception {
        spot1 = this.parkingspotService.createParkingspot(parkinglot.getId(), TYPE.Regular, 50);
    }

    public void booking() throws Exception {
        booking = this.bookingService.book(spot1.getId(), parkinglot.getId(), dateFormat.format(bkdatePlusThreeHours), 2, user.getUsername());
    }

    public void teardown() {
        try {
            deletionService.deleteBooking(booking.getId());
        } catch (Exception e) {

        }

        try {
            deletionService.deleteParkingspot(spot1.getId());
        } catch (Exception e) {

        }

        try {
            deletionService.deleteParkinglot(parkinglot.getId());
        } catch (Exception e) {

        }

        try {
            deletionService.deleteUser(user.getUsername());
        } catch (Exception e) {

        }
    }
}