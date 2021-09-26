package hiof.parking.service;

import hiof.parking.model.Location;
import hiof.parking.model.ROLE;
import hiof.parking.model.User;
import hiof.parking.repository.UserRepo;
import hiof.parking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(String username, String password, String passwordConfirmed, String firstname, String surname, String city, String address, int nummber, int zipcode, String area) throws Exception {
        if (userRepo.findByUsername(username).isPresent())
            throw new IllegalArgumentException("Error creating user!");
        if (password.equals(passwordConfirmed)) {
            Location adr = new Location(city, address, nummber, zipcode, area);
            password = bCryptPasswordEncoder.encode(password);
            User usr = new User(username, password, firstname, surname, adr, setRole());
            var createdUser = userRepo.save(usr);
            return createdUser;
        }
        else
            throw new Exception("Passwords don't match!");
    }

    private ROLE setRole() {
        var all = userRepo.findAll();
        if (all.size() == 0)
            return ROLE.Administrator;
        else
            return ROLE.User;
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        var all = userRepo.findAll();

        if (all.size() == 0)
            throw new IllegalArgumentException("None found!");

        return all;
    }

    public void updateUser(String username, String forename, String surname, String city, String address, int nummber, int zipcode, String area) {
        Location adr = new Location(city, address, nummber, zipcode, area);
        var user= userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirstname(forename);
        user.setSurname(surname);
        user.setLocation(adr);
        userRepo.save(user);
    }

    public void changePassword(String username, String newPass, String newPassConfirmed, String originalPass) throws Exception {
        var user  =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (newPass.equals(newPassConfirmed)) {
            if (bCryptPasswordEncoder.matches(originalPass, user.getPassword())) {
                newPass = bCryptPasswordEncoder.encode(newPass);
                user.setPassword(newPass);
                userRepo.save(user);
            }
        }
        else
            throw new Exception("New passowrd and new password confirmed aren't equal!");
    }
}
