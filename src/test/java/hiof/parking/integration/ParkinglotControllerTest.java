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
public class ParkinglotControllerTest {
    @MockBean
    private IParkinglotService parkinglotService;
    @MockBean
    private IDeletionService deletionService;
    @MockBean
    private IUserService userService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(username = "blabla")
    public void getAll_IsNotFoundWhenNoParkinglotsInDb() throws Exception {
        when(parkinglotService.getAllParkinglots()).thenThrow();

        this.mockMvc
            .perform(
                get("/api/parkinglot/all")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAll_IsFoundWhenParkinglotsExistInDb() throws Exception {
        when(parkinglotService.getAllParkinglots()).thenReturn(List.of(new Parkinglot()));

        this.mockMvc
            .perform(
                get("/api/parkinglot/all")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void create_ReturnsCreatedWhenPassingCorrectArguments() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[]{"blabla", "User"});

            var location = new Location();
            location.setArea("a");
            location.setAddress("a");
            location.setCity("a");
            location.setZipcode(1);
            location.setNumber(1);

            when(parkinglotService.createParkingLot(any(), any())).thenReturn(new Parkinglot());

            var request = new ArrayList<String>();
            request.add(location.getAddress());
            request.add(location.getCity());
            request.add(location.getNumber() + "");
            request.add(location.getZipcode() + "");
            request.add(location.getArea());

            var json = new ObjectMapper().writeValueAsString(request);

            this.mockMvc
                .perform(
                    post("/api/parkinglot/create").content(json).contentType(MediaType.APPLICATION_JSON)
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

            var location = new Location();
            location.setArea("a");
            location.setAddress("a");
            location.setCity("a");
            location.setZipcode(1);
            location.setNumber(1);
            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);

            var json = new ObjectMapper().writeValueAsString(location);

            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(parkinglotService.updateParkinglot(1, "a", "a", 1, 1, "a", "blabla")).thenReturn(new Parkinglot());

            this.mockMvc
                .perform(
                    put("/api/parkinglot/update/1").content(json).contentType(MediaType.APPLICATION_JSON)
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

            var location = new Location();
            location.setArea("a");
            location.setAddress("a");
            location.setCity("a");
            location.setZipcode(1);
            location.setNumber(1);
            var user = new User();
            user.setId(2);
            user.setUsername("blabla");
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);

            var json = new ObjectMapper().writeValueAsString(location);

            when(userService.getByUsername(any())).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);

            this.mockMvc
                .perform(
                    put("/api/parkinglot/update/1").content(json).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOfAUser_IsFoundWhenUserHasParkinglos() throws Exception {
        when(parkinglotService.getAllParkinglotsOfAUser(any())).thenReturn(List.of(new Parkinglot()));

        this.mockMvc
            .perform(
                get("/api/parkinglot/all/aa")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOfAUser_IsNotFoundWhenUserHasNoParkinglos() throws Exception {
        when(parkinglotService.getAllParkinglotsOfAUser(any())).thenThrow();

        this.mockMvc
            .perform(
                get("/api/parkinglot/all/aa")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_IsFound() throws Exception {
        when(parkinglotService.getParkinglotById(1)).thenReturn(new Parkinglot());

        this.mockMvc
            .perform(
                get("/api/parkinglot/get/1")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_IsNotFoundIfParkinglotNotFoundInDb() throws Exception {
        when(parkinglotService.getParkinglotById(1)).thenThrow();

        this.mockMvc
            .perform(
                get("/api/parkinglot/get/1")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void deleteParkinglot_NoContentWhenDeletionOk() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("Administrator", 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "Administrator"});

            var user = new User();
            user.setId(2);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkinglot(1)).thenReturn(true);

            this.mockMvc
                .perform(
                    delete("/api/parkinglot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isNoContent());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void deleteParkinglot_BadRequestWhenDeletionFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("Administrator", 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "Administrator"});

            var user = new User();
            user.setId(2);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkinglot(1)).thenReturn(true);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkinglot(1)).thenReturn(false);

            this.mockMvc
                .perform(
                    delete("/api/parkinglot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void deleteParkinglot_ForbiddenWhenAuthorizationFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrAdmin("User", 2, 2))
                .thenReturn(false);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "User"});

            var user = new User();
            user.setId(2);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);
            when(deletionService.deleteParkinglot(1)).thenReturn(true);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(parkinglotService.getParkinglotById(1)).thenReturn(parkinglot);

            this.mockMvc
                .perform(
                    delete("/api/parkinglot/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
        }
    }
}
