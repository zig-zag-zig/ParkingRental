package hiof.parking.unit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest extends TestFactory{

    @BeforeEach
    void create() throws Exception {
        user();
        parkinglot();
        parkingspot();
        booking();
    }

    @AfterEach
    void deleteObjects() {
        teardown();
    }

    @Test
    void errorWhenCreatingUserWithTakenUsername() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user.getUsername(), "as", "as", "fff", "fa", "fasc", "afsa", 2, 1, "amv");
        });
    }

    @Test
    void getByUsername() throws Exception {
        assertEquals(user.getUsername(), userService.getByUsername(user.getUsername()).getUsername());

        Assertions.assertThrows(Exception.class, () -> {
            userService.getByUsername("nonexistend");
        });
    }

    @Test
    void getAll() throws Exception {
        assertEquals(1, userService.getAllUsers().size());
        assertEquals(user.getId(), userService.getAllUsers().get(0).getId());
    }

    @Test
    void update() throws Exception {
        int newZip = 0000;

        assertNotEquals(newZip, userService.getByUsername(user.getUsername()).getLocation().getZipcode());

        userService.updateUser(user.getUsername(), user.getFirstname(), user.getSurname(), user.getLocation().getCity(), user.getLocation().getAddress(), user.getLocation().getNumber(), newZip, user.getLocation().getArea());

        assertEquals(newZip, userService.getByUsername(user.getUsername()).getLocation().getZipcode());
    }

    @Test
    void delete() throws Exception {
        assertEquals(user.getUsername(), userService.getByUsername(user.getUsername()).getUsername());
        assertEquals(user.getUsername(), parkinglotService.getParkinglotById(parkinglot.getId()).getOwner().getUsername());
        assertEquals(user.getUsername(), bookingService.getBookingById(booking.getId()).getUser().getUsername());

        deletionService.deleteUser(user.getUsername());

        Assertions.assertThrows(Exception.class, () -> {
            userService.getByUsername(user.getUsername());
        });

        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getParkinglotById(parkinglot.getId());
        });

        Assertions.assertThrows(Exception.class, () -> {
            bookingService.getBookingById(booking.getId());
        });
    }
}
