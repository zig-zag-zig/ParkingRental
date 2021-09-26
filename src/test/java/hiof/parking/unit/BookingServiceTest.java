package hiof.parking.unit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class BookingServiceTest extends TestFactory {

    @BeforeEach
    void books() throws Exception {
        user();
        parkinglot();
        parkingspot();
        booking();
        //booking = bookingService.booking(spot1.getId(), parkinglot.getId(), dateFormat.format(bkdatePlusThreeHours), 2, user.getUsername());
    }

    @AfterEach
    void deleteObjects() {
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
}
