package hiof.parking;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.Location;
import hiof.parking.model.TYPE;
import hiof.parking.service.interfaces.IBookingService;
import hiof.parking.service.interfaces.IParkinglotService;
import hiof.parking.service.interfaces.IParkingspotService;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Date;

import static hiof.parking.helpers.DateCheckerHelper.addHoursToAnyTime;
import static hiof.parking.helpers.DateCheckerHelper.dateFormat;

/*@SpringBootApplication
public class ParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }

}*/

@SpringBootApplication
public class ParkingApplication extends SpringBootServletInitializer implements CommandLineRunner {
    @Autowired
    IUserService userService;
    @Autowired
    IParkinglotService parkinglotService;
    @Autowired
    IParkingspotService parkingspotService;
    @Autowired
    IBookingService bookingService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ParkingApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParkingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.getAllUsers();

        } catch (IllegalArgumentException e) {
            var user1 = userService.createUser("admin", "a", "a", "first", "admin", "Fredrikstad", "Veien", 2, 1524, "Selbak");
            var user2 = userService.createUser("user1", "a", "a","second", "user", "Fredrikstad", "Markgata", 5, 1605, "Trara");
            var user3 = userService.createUser("user2", "a", "a","third", "usr", "Oslo", "Blablagata", 12, 1202, "Fake");
            var user4 = userService.createUser("user3", "a", "a","fourth", "uzah", "Drammen", "Ekte", 53, 1100, "Falsk");

            var lot1 = parkinglotService.createParkingLot(new Location("Fredrikstad", "Storgata", 2, 1602, "Hassingen"), user2.getUsername());
            var lot2 = parkinglotService.createParkingLot(new Location("Oslo", "Stedet", 22, 1212, "Blabla"), user3.getUsername());
            var lot3 = parkinglotService.createParkingLot(new Location("Drammen", "Her", 18, 1129, "Oppfunnet"), user4.getUsername());

            parkingspotService.createParkingspot(lot1.getId(), TYPE.Regular, 30);
            parkingspotService.createParkingspot(lot1.getId(), TYPE.Regular, 30);
            parkingspotService.createParkingspot(lot1.getId(), TYPE.Handicap, 10);
            parkingspotService.createParkingspot(lot1.getId(), TYPE.Handicap, 10);
            parkingspotService.createParkingspot(lot1.getId(), TYPE.Truck, 50);

            parkingspotService.createParkingspot(lot2.getId(), TYPE.Regular, 25);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Regular, 25);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Regular, 25);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Handicap, 15);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Handicap, 15);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Truck, 40);
            parkingspotService.createParkingspot(lot2.getId(), TYPE.Truck, 40);

            parkingspotService.createParkingspot(lot3.getId(), TYPE.Regular, 25);
            parkingspotService.createParkingspot(lot3.getId(), TYPE.Handicap, 15);
            parkingspotService.createParkingspot(lot3.getId(), TYPE.Regular, 35);

            lot1 = parkinglotService.getParkinglotById(lot1.getId());
            lot2 = parkinglotService.getParkinglotById(lot2.getId());
            lot3 = parkinglotService.getParkinglotById(lot3.getId());

            bookingService.book(lot1.getSpots().get(0).getId(), lot1.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 4)), 7, user3.getUsername());
            bookingService.book(lot1.getSpots().get(1).getId(), lot1.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 9)), 2, user4.getUsername());
            bookingService.book(lot1.getSpots().get(2).getId(), lot1.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 2)), 2, user4.getUsername());

            bookingService.book(lot2.getSpots().get(0).getId(), lot2.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 4)), 7, user2.getUsername());
            bookingService.book(lot2.getSpots().get(1).getId(), lot2.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 9)), 2, user4.getUsername());
            bookingService.book(lot2.getSpots().get(2).getId(), lot2.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 2)), 2, user4.getUsername());

            bookingService.book(lot3.getSpots().get(0).getId(), lot3.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 4)), 7, user3.getUsername());
            bookingService.book(lot3.getSpots().get(1).getId(), lot3.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 9)), 2, user2.getUsername());
            bookingService.book(lot3.getSpots().get(2).getId(), lot3.getId(), dateFormat.format(addHoursToAnyTime(new Date(), 2)), 2, user2.getUsername());
        }
    }
}
