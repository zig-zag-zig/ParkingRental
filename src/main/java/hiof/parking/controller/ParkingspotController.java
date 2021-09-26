package hiof.parking.controller;

import hiof.parking.model.Parkingspot;
import hiof.parking.model.TYPE;
import hiof.parking.service.interfaces.IDeletionServiceSpot;
import hiof.parking.service.interfaces.IParkinglotService;
import hiof.parking.service.interfaces.IParkingspotService;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static hiof.parking.helpers.AuthorizationHelper.currentUserOrAdmin;
import static hiof.parking.helpers.AuthorizationHelper.getCurrentUserInfo;

@RestController
@RequestMapping("api/parkingspot")
public class ParkingspotController {
    private IParkingspotService parkinspotService;
    private IParkinglotService parkinglotService;
    private IDeletionServiceSpot deletionService;
    private final IUserService userService;

    @Autowired
    public ParkingspotController(IParkingspotService parkinspotService, IParkinglotService parkinglotService, IDeletionServiceSpot deletionService, IUserService userService) {
        this.parkinspotService = parkinspotService;
        this.parkinglotService = parkinglotService;
        this.deletionService = deletionService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Parkingspot> create(@RequestBody List<String> spotInfo) {
        try {
            var userInfo = getCurrentUserInfo();
            var idOfCurrentUser = userService.getByUsername(userInfo[0]).getId();
            var lotId = Long.parseLong(spotInfo.get(0).trim());
            var parkinglot = parkinglotService.getParkinglotById(lotId);
            var type = TYPE.valueOf(spotInfo.get(1).trim());
            var hourlyPrice = Integer.parseInt(spotInfo.get(2).trim());
            if (currentUserOrAdmin(userInfo[1], idOfCurrentUser, parkinglot.getOwner().getId())) {
                var spot = parkinspotService.createParkingspot(lotId, type, hourlyPrice);
                return new ResponseEntity<>(spot, HttpStatus.CREATED);
            } else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Authorized!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/update/{spotId}")
    public ResponseEntity<Void> update(@PathVariable long spotId, @RequestBody List<String> spotInfo) {
        try {
            var type = TYPE.valueOf(spotInfo.get(0).trim());
            var hourlyPrice = Integer.parseInt(spotInfo.get(1).trim());
            parkinspotService.updateParkingspot(spotId, type, hourlyPrice);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{spotId}")
    public ResponseEntity<Void> delete(@PathVariable String spotId) {
        try {
            var spotIdParsed = Long.parseLong(spotId.trim());
            deletionService.deleteParkingspot(spotIdParsed);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/get/{spotId}")
    public ResponseEntity<Parkingspot> get(@PathVariable String spotId) {
        try {
            var spotIdParsed = Long.parseLong(spotId.trim());
            var spot = parkinspotService.getParkingspotById(spotIdParsed);
            return new ResponseEntity<>(spot, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parkingspot>> getAll() {
        try {
            var allSpots = parkinspotService.getAll();
            return new ResponseEntity<>(allSpots, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
