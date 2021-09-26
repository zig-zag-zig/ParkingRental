package hiof.parking.controller;

import hiof.parking.model.User;
import hiof.parking.service.interfaces.IDeletionServiceUser;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;
    private final IDeletionServiceUser deletionService;

    @Autowired
    public UserController(IUserService userService, IDeletionServiceUser deletionService) {
        this.userService = userService;
        this.deletionService = deletionService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody List<String> userInfo) {
        try {
            var username = userInfo.get(0).trim();
            var password = userInfo.get(1).trim();
            var passwordConfirmed = userInfo.get(2).trim();
            var firstname = userInfo.get(3).trim();
            var surname = userInfo.get(4).trim();
            var city = userInfo.get(5).trim();
            var address = userInfo.get(6).trim();
            var number = Integer.parseInt(userInfo.get(7).trim());
            var zip = Integer.parseInt(userInfo.get(8).trim());
            var area = userInfo.get(9).trim();
            var user = userService.createUser(username,password, passwordConfirmed, firstname, surname, city, address, number, zip, area);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('Administrator') or #username.trim().equalsIgnoreCase(authentication.name)")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        try {
            deletionService.deleteUser(username.trim());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/update/{username}")
    @PreAuthorize("hasAuthority('Administrator') or #username.trim().equalsIgnoreCase(authentication.name)")
    public ResponseEntity<Void> update(@PathVariable String username, @RequestBody List<String> userInfo) {
        try {
            var firstname = userInfo.get(0).trim();
            var surname = userInfo.get(1).trim();
            var city = userInfo.get(2).trim();
            var address = userInfo.get(3).trim();
            var number = Integer.parseInt(userInfo.get(4).trim());
            var zip = Integer.parseInt(userInfo.get(5).trim());
            var area = userInfo.get(6).trim();
            userService.updateUser(username.trim(), firstname, surname, city, address, number, zip, area);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasAuthority('Administrator') or #username.trim().equalsIgnoreCase(authentication.name)")
    public ResponseEntity<User> get(@PathVariable String username) {
        try {
            var user = userService.getByUsername(username.trim());
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        try {
            var usernameOfCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            var user = userService.getByUsername(usernameOfCurrentUser);
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Administrator')")
    public ResponseEntity<List<User>> getAll() {
        try {
            var allUsers = userService.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
