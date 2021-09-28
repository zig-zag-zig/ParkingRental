package hiof.parking.controller;

import hiof.parking.model.Location;
import hiof.parking.model.Parkinglot;
import hiof.parking.model.ROLE;
import hiof.parking.service.interfaces.IDeletionServiceLot;
import hiof.parking.service.interfaces.IParkinglotService;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static hiof.parking.helpers.AuthorizationHelper.currentUserOrAdmin;
import static hiof.parking.helpers.AuthorizationHelper.getCurrentUserInfo;
import static hiof.parking.helpers.ExceptionThrowerHelper.throwCorrectException;

@RestController
@RequestMapping("api/parkinglot")
public class ParkinglotController {
    private IParkinglotService parkinglotService;
    private IUserService userService;
    private IDeletionServiceLot deletionService;

    @Autowired
    public ParkinglotController(IParkinglotService parkinglotService, IUserService userService, IDeletionServiceLot deletionService) {
        this.parkinglotService = parkinglotService;
        this.userService = userService;
        this.deletionService = deletionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Parkinglot> create(@RequestBody List<String> lotInfo) {
        try {
            var userInfo = getCurrentUserInfo();

            var city = lotInfo.get(0).trim();
            var address = lotInfo.get(1).trim();
            var number = Integer.parseInt(lotInfo.get(2).trim());
            var zip = Integer.parseInt(lotInfo.get(3).trim());
            var area = lotInfo.get(4).trim();
            var location = new Location(city, address, number, zip, area);

            String username = userInfo[0];

            if (userInfo[1].equalsIgnoreCase(ROLE.Administrator.toString())) {
                username = lotInfo.get(5).trim();
            }

            var newParkinglot = parkinglotService.createParkingLot(location, username);
            return new ResponseEntity<>(newParkinglot, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{parkinglotId}")
    public ResponseEntity<Void> delete(@PathVariable long parkinglotId) throws Exception {
        try {
            if (isAllowed(parkinglotId)) {
                if (deletionService.deleteParkinglot(parkinglotId))
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete");
            } else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authorized!");
        } catch (Exception e) {
            throw throwCorrectException(e);
        }
    }

    private boolean isAllowed(long parkinglotId) {
        var userInfo = getCurrentUserInfo();
        var idOfCurrentUser = userService.getByUsername(userInfo[0]).getId();
        var parkinglot = parkinglotService.getParkinglotById(parkinglotId);
        return currentUserOrAdmin(userInfo[1], idOfCurrentUser, parkinglot.getOwner().getId());
    }

    @PutMapping("/update/{parkinglotId}")
    public ResponseEntity<Void> update(@PathVariable long parkinglotId, @RequestBody Location location) throws Exception {
        try {
            if (isAllowed(parkinglotId)) {
                var username = getCurrentUserInfo()[0];
                parkinglotService.updateParkinglot(parkinglotId, location.getCity(), location.getAddress(), location.getNumber(), location.getZipcode(), location.getArea(), username);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authorized!");
        } catch (Exception e) {
            throw throwCorrectException(e);
        }
    }

    @GetMapping("/get/{parkinglotId}")
    public ResponseEntity<Parkinglot> get(@PathVariable long parkinglotId) {
        try {
            var parkinglot = parkinglotService.getParkinglotById(parkinglotId);
            return new ResponseEntity<>(parkinglot, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parkinglot>> getAll() {
        try {
            var allParkinglots = parkinglotService.getAllParkinglots();
            return new ResponseEntity<>(allParkinglots, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<List<Parkinglot>> getAllOfUser(@PathVariable String username) {
        try {
            var allParkinglots = parkinglotService.getAllParkinglotsOfAUser(username);
            return new ResponseEntity<>(allParkinglots, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
