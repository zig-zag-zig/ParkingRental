package hiof.parking.service.interfaces;

import hiof.parking.model.User;

import java.util.List;

public interface IUserService {
    User createUser(String username, String password, String passwordConfirmed, String firstname, String surname, String city, String address, int nummber, int zipcode, String area) throws Exception;

    User getByUsername(String username);

    List<User> getAllUsers();

    User updateUser(String username, String forename, String surname, String city, String address, int nummber, int zipcode, String area);

    boolean changePassword(String username, String newPass, String newPassConfirmed, String originalPass) throws Exception;
}
