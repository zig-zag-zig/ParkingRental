package hiof.parking.unit;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.Booking;
import hiof.parking.model.Parkingspot;
import hiof.parking.model.TYPE;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class BookingServiceTest extends TestFactory {
    List<Parkingspot> onlyAvailable;
    Parkingspot spot2;
    Booking booking2;

    @BeforeEach
    void books() throws Exception {
        onlyAvailable = new ArrayList<>();
        user();
        parkinglot();
        parkingspot();
        booking();
        spot2 = parkingspotService.createParkingspot(parkinglot.getId(), TYPE.Handicap, 50);
    }

    @AfterEach
    void deleteObjects() {
        deletionService.deleteParkingspot(spot2.getId());
        teardown();
    }

    @Test
    void bookingInfoIsCorrect() {
        assertEquals((booking.getDateAndTime().size() * 50), bookingService.getBookingById(booking.getId()).getPrice());
        assertEquals(booking.getSpot().getId(), spot1.getId());
        assertEquals(booking.getUser(), user);
    }

    @Test
    void getById() {
        assertEquals(booking.getId(), bookingService.getBookingById(booking.getId()).getId());
    }

    @Test
    void getAll() throws Exception {
        assertEquals(1, bookingService.getAllBookings().size());
        assertEquals(booking.getId(), bookingService.getAllBookings().get(0).getId());
        assertEquals(booking.getParkinglot().getId(), bookingService.getAllBookings().get(0).getParkinglot().getId());
        assertEquals(booking.getUser().getId(), bookingService.getAllBookings().get(0).getUser().getId());
        assertEquals(booking.getSpot().getId(), bookingService.getAllBookings().get(0).getSpot().getId());
    }

    @Test
    void fetchingAllTheBookingsOfOwnedSpotsFailsWhenNoneAvailable() throws Exception {
        var tempOwner = userService.createUser("aax", "a", "a", "First", "Usah", "Home", "ff", 2, 1001, "dd");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.getAllBookingsOfOwnedSpots(tempOwner.getUsername());
        });
        deletionService.deleteUser(tempOwner.getUsername());
    }

    @Test
    void fetchingAllTheBookingsOfOwnedSpots() {
        assertEquals(bookingService.getAllBookings().size(), bookingService.getAllBookingsOfOwnedSpots(user.getUsername()).size());
        assertEquals(user, bookingService.getAllBookingsOfOwnedSpots(user.getUsername()).get(0).getParkinglot().getOwner());
    }

    @Test
    void errorWhenTryingToBookAlreadyBooked() {
        Assertions.assertThrows(Exception.class, () -> {
            bookingService.book(spot1.getId(),  parkinglot.getId(), booking.getDateAndTime().get(0), 2, user.getUsername());
        });
    }

    @Test
    void errorWhenTryingToBookDateAfterLastDateOfSchedule() {
        Assertions.assertThrows(Exception.class, () -> {
            bookingService.book(spot1.getId(), parkinglot.getId(), dateFormat.format(bkdatePlusThreeHours), 5, user.getUsername());
        });
    }

    @Test
    void exceptionWhenNotFindingBookingToDelete() {
        Assertions.assertThrows(Exception.class, () -> {
            deletionService.deleteBooking(1090);
        });
    }

    @Test
    void delete() throws Exception {
        assertEquals(booking.getId(), bookingService.getAllBookings().get(0).getId());

        deletionService.deleteBooking(booking.getId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.getAllBookings();
        });

        availableToBookAgainAfterDeletingBooking();
    }

    void availableToBookAgainAfterDeletingBooking() throws Exception {
        var newbooking = bookingService.book(spot1.getId(), parkinglot.getId(), dateFormat.format(bkdatePlusThreeHours), 2, user.getUsername());
        assertEquals(newbooking.getId(), bookingService.getAllBookings().get(0).getId());
    }

    @Test
    public void beforeBookingAllTypes() throws Exception {
        onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglot.getId(), DateCheckerHelper.dateFormat.format(bkdatePlusThreeHours), 6, null);
        assertEquals(1, onlyAvailable.size());
        assertEquals(spot2.getId(), onlyAvailable.get(0).getId());
    }

    @Test
    public void beforeBookingOneType() throws Exception {
        onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglot.getId(), DateCheckerHelper.dateFormat.format(bkdatePlusThreeHours), 6, spot2.getType());
        assertEquals(1, onlyAvailable.size());
        assertEquals(spot2.getId(), onlyAvailable.get(0).getId());
    }


    @Test
    public void afterBookingOneType() throws Exception {
        booking2 = bookingService.book(spot2.getId(), parkinglot.getId(), DateCheckerHelper.dateFormat.format(bkdatePlusThreeHours), 2, user.getUsername());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglot.getId(), DateCheckerHelper.dateFormat.format(new Date()), 6, spot2.getType());
        });

        assertEquals(0, onlyAvailable.size());

        deletionService.deleteBooking(booking2.getId());
    }


    @Test
    public void afterBookingAllTypes() throws Exception {
        booking2 = bookingService.book(spot2.getId(), parkinglot.getId(), DateCheckerHelper.dateFormat.format(bkdatePlusThreeHours), 2, user.getUsername());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglot.getId(), DateCheckerHelper.dateFormat.format(new Date()), 6, null);
        });

        assertEquals(0, onlyAvailable.size());

        deletionService.deleteBooking(booking2.getId());
    }

    @Test
    public void afterCancellingBookingsAndGettingAllTypes() throws Exception {
        deletionService.deleteBooking(booking.getId());
        onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglot.getId(), DateCheckerHelper.dateFormat.format(new Date()), 6, null);
        assertEquals(2, onlyAvailable.size());
        assertEquals(spot1.getId(), onlyAvailable.get(0).getId());
        assertEquals(spot2.getId(), onlyAvailable.get(1).getId());
    }
}
