package hiof.parking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hiof.parking.helpers.AuthorizationHelper;
import hiof.parking.model.*;
import hiof.parking.service.interfaces.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ParkingspotControllerTest {
    @MockBean
    private IParkinglotService parkinglotService;
    @MockBean
    private IParkingspotService parkingspotService;
    @MockBean
    private IDeletionService deletionService;
    @MockBean
    private IUserService userService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(username = "blabla")
    public void create_ReturnsCreatedWhenPassingCorrectArguments() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 1, 1))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(1);
            var parkinglot = new Parkinglot();
            parkinglot.setId(1);
            parkinglot.setOwner(user);

            var request = new ArrayList<String>();
            request.add("1");
            request.add("Regular");
            request.add("1");

            var json = new ObjectMapper().writeValueAsString(request);

            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);

            this.mockMvc
                .perform(
                    post("/api/parkingspot/create").content(json).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isCreated());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void update_NoContentWhenOk() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            parkinglot.setId(1);
            var parkingspot = new Parkingspot();
            parkingspot.setParkinglotId(parkinglot.getId());
            parkingspot.setId(1);

            var json = new ObjectMapper().writeValueAsString(new String[] { "Regular", "1" });

            when(parkingspotService.getParkingspotById(1)).thenReturn(parkingspot);
            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(parkingspotService.updateParkingspot(1, TYPE.Regular, 1)).thenReturn(new Parkingspot());

            this.mockMvc
                .perform(
                    put("/api/parkingspot/update/1").content(json).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isNoContent());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void update_ForbiddenWhenAuthorizationFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(false);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            parkinglot.setId(1);
            var parkingspot = new Parkingspot();
            parkingspot.setParkinglotId(parkinglot.getId());
            parkingspot.setId(1);

            var json = new ObjectMapper().writeValueAsString(new String[] { "Regular", "1" });

            when(parkingspotService.getParkingspotById(1)).thenReturn(parkingspot);
            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);

            this.mockMvc
                .perform(
                    put("/api/parkingspot/update/1").content(json).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_IsFound() throws Exception {
        when(parkingspotService.getParkingspotById(1)).thenReturn(new Parkingspot());

        this.mockMvc
            .perform(
                get("/api/parkingspot/get/1")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_IsNotFoundIfParkingspotNotFoundInDb() throws Exception {
        when(parkingspotService.getParkingspotById(1)).thenThrow();

        this.mockMvc
            .perform(
                get("/api/parkingspot/get/1")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void deleteParkingspot_NoContentWhenDeletionOk() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            parkinglot.setId(1);
            var parkingspot = new Parkingspot();
            parkingspot.setParkinglotId(parkinglot.getId());
            parkingspot.setId(1);

            var json = new ObjectMapper().writeValueAsString(new String[] { "Regular", "1" });

            when(parkingspotService.getParkingspotById(1)).thenReturn(parkingspot);
            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkingspot(1)).thenReturn(true);

            this.mockMvc
                .perform(
                    delete("/api/parkingspot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isNoContent());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void deleteParkingspot_BadRequestWhenDeletionFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            parkinglot.setId(1);
            var parkingspot = new Parkingspot();
            parkingspot.setParkinglotId(parkinglot.getId());
            parkingspot.setId(1);

            var json = new ObjectMapper().writeValueAsString(new String[] { "Regular", "1" });

            when(parkingspotService.getParkingspotById(1)).thenReturn(parkingspot);
            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkingspot(1)).thenReturn(false);

            this.mockMvc
                .perform(
                    delete("/api/parkingspot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void deleteParkingspot_ForbiddenWhenAuthorizationFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(false);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            parkinglot.setId(1);
            var parkingspot = new Parkingspot();
            parkingspot.setParkinglotId(parkinglot.getId());
            parkingspot.setId(1);

            var json = new ObjectMapper().writeValueAsString(new String[] { "Regular", "1" });

            when(parkingspotService.getParkingspotById(1)).thenReturn(parkingspot);
            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);

            this.mockMvc
                .perform(
                    delete("/api/parkingspot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
        }
    }
}