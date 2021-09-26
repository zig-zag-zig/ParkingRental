package hiof.parking.unit;

import hiof.parking.helpers.AuthorizationHelper;
import hiof.parking.model.ROLE;
import hiof.parking.model.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class AuthorizationHelperTest extends TestFactory {
    User admin;
    User user2;
    User user3;
    User user4;

    @BeforeEach
    void onStartup() throws Exception {
        user();
        parkinglot();
        parkingspot();
        booking();

        admin = userService.createUser("Second", "a", "a", "User", "Fakestad", "Mn kl", "ads", 8796, 12, "da");
        user2 = userService.createUser("Third", "a", "a", "User", "Fakestad", "Mn kl", "ads", 8796, 12, "da");
        user3 = userService.createUser("Fourth", "a", "a", "User", "Fakestad", "Mn kl", "ads", 8796, 12, "da");
        user4 = userService.createUser("Fifth", "a", "a", "User", "Fakestad", "Mn kl", "ads", 8796, 12, "da");
        admin.setRole(ROLE.Administrator);
    }

    @AfterEach
    void onTeardown() {
        deletionService.deleteUser(user2.getUsername());
        deletionService.deleteUser(admin.getUsername());
        deletionService.deleteUser(user3.getUsername());
        deletionService.deleteUser(user4.getUsername());

        teardown();
    }

    @Test
    void currentUserOrParkinglotOwnerOrAdmin() {
        assertTrue(AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin(admin.getRole().toString(), user.getId(), user2.getId(), parkinglot.getOwner().getId()));
    }

    @Test
    void currentUserOrParkinglotOwnerOrAdminOk() {
        assertTrue(AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin(user2.getRole().toString(), user.getId(), user2.getId(), parkinglot.getOwner().getId()));
    }

    @Test
    void currentUserOrParkinglotOwnerOrAdminFalse() {
        assertFalse(AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin(user2.getRole().toString(), user2.getId(), user4.getId(), parkinglot.getOwner().getId()));
    }

    @Test
    void currentUserOrAdmin() {
        assertTrue(AuthorizationHelper.currentUserOrAdmin(admin.getRole().toString(), admin.getId(), user3.getId()));
    }

    @Test
    void currentUserOrAdminOk() {
        assertTrue(AuthorizationHelper.currentUserOrAdmin(user4.getRole().toString(), user4.getId(), user4.getId()));
    }

    @Test
    void currentUserOrAdminFalse() {
        assertFalse(AuthorizationHelper.currentUserOrAdmin(user4.getRole().toString(), user4.getId(), user3.getId()));
    }
}
