package hiof.parking.unit;

import hiof.parking.model.TYPE;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ParkingspotServiceTest extends TestFactory{

    @BeforeEach
    void onSetup() throws Exception {
        user();
        parkinglot();
        parkingspot();
        booking();
    }

    @AfterEach
    void onTeardown() {
        teardown();
    }

    @Test
    void getById() throws Exception {
        assertEquals(spot1.getId(), parkingspotService.getParkingspotById(spot1.getId()).getId());

        Assertions.assertThrows(Exception.class, () -> {
            parkingspotService.getParkingspotById(spot1.getId() + 500);
        });
    }

    @Test
    void update() throws Exception {
        assertNotEquals(TYPE.Handicap, parkingspotService.getParkingspotById(spot1.getId()).getType());
        parkingspotService.updateParkingspot(spot1.getId(), TYPE.Handicap, spot1.getHourlyPrice());
        assertEquals(TYPE.Handicap, parkingspotService.getParkingspotById(spot1.getId()).getType());
    }

    @Test
    void delete() {
        assertEquals(spot1.getId(), bookingService.getBookingById(booking.getId()).getSpot().getId());
        assertEquals(spot1.getId(), parkinglotService.getParkinglotById(parkinglot.getId()).getSpots().get(0).getId());

        assertTrue(parkinglotService.getParkinglotById(parkinglot.getId()).getSpots().size() > 0);

        deletionService.deleteParkingspot(spot1.getId());

        assertFalse(parkinglotService.getParkinglotById(parkinglot.getId()).getSpots().size() > 0);

        Assertions.assertThrows(Exception.class, () -> {
            bookingService.getAllBookings();
        });
    }
}
