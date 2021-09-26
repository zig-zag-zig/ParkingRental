package hiof.parking.unit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ParkinglotServiceTest extends TestFactory{

    @BeforeEach
    void onSetupd() throws Exception {
        user();
        parkinglot();
        parkingspot();
        booking();
    }

    @AfterEach
    void onTeardown() throws Exception {
        teardown();
    }


    @Test
    void getById() {
        assertEquals(parkinglot.getId(), parkinglotService.getParkinglotById(parkinglot.getId()).getId());

        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getParkinglotById(parkinglot.getId() + 500);
        });
    }

    @Test
    void getAll() throws Exception {
        assertEquals(1, parkinglotService.getAllParkinglots().size());
    }

    @Test
    void update() throws Exception {
        String newCity = "New City";
        assertNotEquals(newCity, parkinglotService.getParkinglotById(parkinglot.getId()).getLocation().getCity());
        parkinglotService.updateParkinglot(parkinglot.getId(), newCity, parkinglot.getLocation().getAddress(), parkinglot.getLocation().getNumber(), parkinglot.getLocation().getZipcode(), parkinglot.getLocation().getArea(), user.getUsername());
        assertEquals(newCity, parkinglotService.getParkinglotById(parkinglot.getId()).getLocation().getCity());
    }

    @Test
    void delete() throws Exception {
        assertEquals(parkinglot.getId(), bookingService.getBookingById(booking.getId()).getParkinglot().getId());

        deletionService.deleteParkinglot(parkinglot.getId());

        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getAllParkinglots();
        });

        Assertions.assertThrows(Exception.class, () -> {
            bookingService.getAllBookings();
        });
    }
}
