package hiof.parking.unit;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.helpers.LotAndSpotHelper;
import hiof.parking.model.Parkinglot;
import hiof.parking.model.Parkingspot;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

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
    void onTeardown() {
        teardown();
    }

    @Test
    void getById() {
        var lot = parkinglotService.getParkinglotById(parkinglot.getId());

        assertEquals(parkinglot.getId(), lot.getId());
    }

    @Test
    void getById_ExceptionWhenNotFound() {
        assertEquals(parkinglot.getId(), parkinglotService.getParkinglotById(parkinglot.getId()).getId());

        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getParkinglotById(parkinglot.getId() + 500);
        });
    }

    @Test
    void getAll() {
        var all = parkinglotService.getAllParkinglots();
        assertEquals(1, all.size());
    }

    @Test
    void getAll_ExceptionWhenNoneFound() {
        teardown();
        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getAllParkinglots();
        });
    }

    @Test
    void update() {
        String newCity = "New City";
        assertNotEquals(newCity, parkinglotService.getParkinglotById(parkinglot.getId()).getLocation().getCity());
        parkinglotService.updateParkinglot(parkinglot.getId(), newCity, parkinglot.getLocation().getAddress(), parkinglot.getLocation().getNumber(), parkinglot.getLocation().getZipcode(), parkinglot.getLocation().getArea(), user.getUsername());
        assertEquals(newCity, parkinglotService.getParkinglotById(parkinglot.getId()).getLocation().getCity());
    }

    @Test
    void delete() {
        assertEquals(parkinglot.getId(), bookingService.getBookingById(booking.getId()).getParkinglot().getId());

        deletionService.deleteParkinglot(parkinglot.getId());

        Assertions.assertThrows(Exception.class, () -> {
            parkinglotService.getAllParkinglots();
        });

        Assertions.assertThrows(Exception.class, () -> {
            bookingService.getAllBookings();
        });
    }

    @Test
    void delete_ExceptionWhenParkinglotDoesNotExist() {
        assertFalse(parkinglotService.getAllParkinglots().isEmpty());

        Assertions.assertThrows(Exception.class, () -> {
            deletionService.deleteParkinglot(parkinglot.getId() * 5);
        });
    }

    @Test
    void expandSchedule() throws Exception {
        var sizeOfScehduleBefore = parkingspotService.getParkingspotById(spot1.getId()).getSchedule().size();

        parkinglotService.expandScheduleOfParkingspots(spot1.getParkinglotId(), 365);

        var sizeOfScehduleAfter = parkingspotService.getParkingspotById(spot1.getId()).getSchedule().size();
        assertEquals(sizeOfScehduleAfter, sizeOfScehduleBefore * 2);
    }

    @Test
    void removeOldDatesFromSchedule() throws Exception {
        var parkinglotTemp = new Parkinglot();
        var spotTemp = new Parkingspot();
        var spotTemp2 = new Parkingspot();
        SortedMap<String, Boolean> schedule = new ConcurrentSkipListMap<>();
        var now = new Date();
        var past = DateCheckerHelper.addHoursToAnyTime(now, -1);
        var pastString = DateCheckerHelper.dateFormat.format(past);
        var nowString = DateCheckerHelper.dateFormat.format(now);

        schedule.put(pastString, true);
        schedule.put(nowString, true);

        spotTemp.setSchedule(schedule);
        spotTemp2.setSchedule(schedule);
        parkinglotTemp.setSpots(List.of(spotTemp, spotTemp2));

        assertEquals(2, parkinglotTemp.getSpots().get(0).getSchedule().size());
        assertEquals(pastString, parkinglotTemp.getSpots().get(0).getSchedule().firstKey());
        assertEquals(2, parkinglotTemp.getSpots().get(1).getSchedule().size());
        assertEquals(pastString, parkinglotTemp.getSpots().get(1).getSchedule().firstKey());

        LotAndSpotHelper.removeOldDatesFromSchedule(parkinglotTemp);

        assertEquals(1, parkinglotTemp.getSpots().get(0).getSchedule().size());
        assertEquals(nowString, parkinglotTemp.getSpots().get(0).getSchedule().firstKey());
        assertEquals(1, parkinglotTemp.getSpots().get(1).getSchedule().size());
        assertEquals(nowString, parkinglotTemp.getSpots().get(1).getSchedule().firstKey());
    }
}
