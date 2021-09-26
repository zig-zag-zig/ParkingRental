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

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class OnlyAvialableTest extends TestFactory {
    List<Parkingspot> onlyAvailable;
    Parkingspot spot2;
    Booking booking2;

    @BeforeEach
    void onSetup() throws Exception {
        onlyAvailable = new ArrayList<>();
        user();
        parkinglot();
        parkingspot();
        booking();
        spot2 = parkingspotService.createParkingspot(parkinglot.getId(), TYPE.Handicap, 50);
    }

    @AfterEach
    void onTeardown() {
        deletionService.deleteParkingspot(spot2.getId());

        teardown();
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
