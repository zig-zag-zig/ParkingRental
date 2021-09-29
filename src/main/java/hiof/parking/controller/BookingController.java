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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static hiof.parking.helpers.AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin;
import static hiof.parking.helpers.AuthorizationHelper.getCurrentUserInfo;
import static hiof.parking.helpers.ExceptionThrowerHelper.throwCorrectException;

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
            type = type.trim();
            List<Parkingspot> onlyAvailable = null;
            if (type.equalsIgnoreCase("All types"))
                onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglotId, date, hours, null);
            else {
                var typeParsedToEnum = TYPE.valueOf(type);
                onlyAvailable = bookingService.getOnlyAvailableParkingspotsInAParkinglot(parkinglotId, date, hours, typeParsedToEnum);
            }
            return new ResponseEntity<>(onlyAvailable, HttpStatus.OK);
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
    public ResponseEntity<Booking> get(@PathVariable long bookingId) throws Exception {
        try {
            if (isAllowed(bookingId)) {
                var booking = bookingService.getBookingById(bookingId);
                return new ResponseEntity<>(booking, HttpStatus.OK);
            } else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authorized!");
        } catch (Exception e) {
            throw throwCorrectException(e);
        }
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable long bookingId) throws Exception {
        try {
            if (isAllowed(bookingId)) {
                if (deletionService.deleteBooking(bookingId))
                        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting the booking");
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authorized!");
            }
        } catch (Exception e) {
            throw throwCorrectException(e);
        }
    }

    private boolean isAllowed(long bookingId) {
        var userInfo = getCurrentUserInfo();
        var idOfCurrentUser = userService.getByUsername(userInfo[0]).getId();
        var booking = bookingService.getBookingById(bookingId);
        return currentUserOrParkinglotOwnerOrAdmin(userInfo[1], idOfCurrentUser, booking.getParkinglot().getOwner().getId(), booking.getUser().getId());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAll() {
        try {
            var userInfo = getCurrentUserInfo();

            List<Booking> allBookings;

            if (userInfo[1].equalsIgnoreCase(ROLE.Administrator.toString()))
                allBookings = bookingService.getAllBookings();
            else
                allBookings = bookingService.getAllBookingsOfAUser(userInfo[0]);

            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/all/{username}")
    @PreAuthorize("hasAuthority('Administrator') or #username.trim().equalsIgnoreCase(authentication.name)")
    public ResponseEntity<List<Booking>> getAllOfAUser(@PathVariable String username) {
        try {
            var allBookings = bookingService.getAllBookingsOfAUser(username.trim());
            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
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

    @GetMapping("/bookingsOnOwnedSpots")
    public ResponseEntity<List<Booking>> getAllOnOwnedSpotsCurrentUser() {
        try {
            var userInfo = getCurrentUserInfo();
            var allBookings = bookingService.getAllBookingsOfOwnedSpots(userInfo[0]);
            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
