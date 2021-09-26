package hiof.parking.controller;

import hiof.parking.model.Booking;
import hiof.parking.model.Parkingspot;
import hiof.parking.model.ROLE;
import hiof.parking.model.TYPE;
import hiof.parking.service.interfaces.IBookingService;
import hiof.parking.service.interfaces.IDeletionServiceBooking;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;
import java.util.Collection;
import java.util.List;
import static hiof.parking.helpers.AuthorizationHelper.*;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    private IBookingService bookingService;
    private IDeletionServiceBooking deletionService;
    private IUserService userService;

    @Autowired
    public BookingController(IBookingService IBookingService, IDeletionServiceBooking deletionService, IUserService userService) {
        this.bookingService = IBookingService;
        this.deletionService = deletionService;
        this.userService = userService;
    }

    @GetMapping("/onlyavailable/{parkinglotId}/{type}/{date}/{hours}")
    public ResponseEntity<List<Parkingspot>> getOnlyAvailable(@PathVariable long parkinglotId, @PathVariable String type, @PathVariable String date, @PathVariable int hours) {
        try {
            String typeOfSpot = "";
            type = type.trim();
            List<Parkingspot> onlyAvailable = null;
            if (type.equalsIgnoreCase("All types"))
                onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglotId, date, hours, null);
            else {
                var typeParsedToEnum = TYPE.valueOf(type.trim());
                onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglotId, date, hours, typeParsedToEnum);
            }
            return new ResponseEntity<>(onlyAvailable, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> create(@RequestBody List<String> bookingInfo) {
        try {
            var userInfo = getCurrentUserInfo();

            var spotId = Long.parseLong(bookingInfo.get(0).trim());
            var parkingLotId = Long.parseLong(bookingInfo.get(1).trim());
            var bookingDate = bookingInfo.get(2).trim();
            var hours = Integer.parseInt(bookingInfo.get(3).trim());
            String username = "";
            if (userInfo[1].equalsIgnoreCase(ROLE.Administrator.toString()))
                username = bookingInfo.get(4).trim();
            else
                username = userInfo[0];
            var newBooking = bookingService.book(spotId, parkingLotId, bookingDate, hours, username);
            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @GetMapping("/get/{bookingId}")
    public ResponseEntity<Booking> get(@PathVariable String bookingId) {
        try {
            var userInfo = getCurrentUserInfo();
            var idOfCurrentUser = userService.getByUsername(userInfo[0]).getId();
            var bookingIdParsed = Long.parseLong(bookingId.trim());
            var booking = bookingService.getBookingById(bookingIdParsed);
            if (currentUserOrParkinglotOwnerOrAdmin(userInfo[1], idOfCurrentUser, booking.getParkinglot().getOwner().getId(), booking.getUser().getId())) {
                return new ResponseEntity<>(booking, HttpStatus.FOUND);
            } else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Authorized!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable String bookingId) {
        try {
            var userInfo = getCurrentUserInfo();
            var idOfCurrentUser = userService.getByUsername(userInfo[0]).getId();
            var bookingIdParsed = Long.parseLong(bookingId.trim());
            var booking = bookingService.getBookingById(bookingIdParsed);
            if (currentUserOrParkinglotOwnerOrAdmin(userInfo[0], idOfCurrentUser, booking.getParkinglot().getOwner().getId(), booking.getUser().getId())) {
                deletionService.deleteBooking(bookingIdParsed);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Authorized!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(value = {"/all", "/all/{username}"})
    public ResponseEntity<List<Booking>> getAllOfUser(@PathVariable(required = false) String username) {
        try {
            var userInfo = getCurrentUserInfo();

            var allBookings = getAllBookings(username, userInfo[0], userInfo[1]);

            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private List<Booking> getAllBookings(String username, String usernameOfCurrentUser, String roleOfUser) {
        if (roleOfUser.equalsIgnoreCase(ROLE.Administrator.toString())) {
            if (username != null) {
                return bookingService.getAllBookingsOfAUser(username.trim());
            } else {
                return bookingService.getAllBookings();
            }
        } else {
            return bookingService.getAllBookingsOfAUser(usernameOfCurrentUser);
        }
    }

    @GetMapping("/bookingsOnOwnedSpots/{username}")
    @PreAuthorize("hasAuthority('Administrator') or #username.trim().equalsIgnoreCase(authentication.name)")
    public ResponseEntity<List<Booking>> getAllOnOwnedSpots(@PathVariable String username) {
        try {
            var allBookings = bookingService.getAllBookingsOfOwnedSpots(username.trim());
            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/bookingsOnOwnedSpotsCurrent/")
    public ResponseEntity<List<Booking>> getAllOnOwnedSpotsCurrentUser() {
        try {
            var userInfo = getCurrentUserInfo();
            var allBookings = bookingService.getAllBookingsOfOwnedSpots(userInfo[0]);
            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/write")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<Void> writeBookingsToCSV() {
        try {
            bookingService.writeBookingsToCsv(new File("bookings.csv"));
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
