package hiof.parking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hiof.parking.controller.BookingController;
import hiof.parking.helpers.AuthorizationHelper;
import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.*;
import hiof.parking.service.*;
import hiof.parking.service.interfaces.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @MockBean
    private IBookingService bookingService;
    @MockBean
    private IDeletionService deletionService;
    @MockBean
    private IUserService userService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(authorities ="Administrator", username = "blabla")
    public void getAll_NotFoundWhenNoBookingsInDb() throws Exception {
        when(bookingService.getAllBookings()).thenThrow();

        this.mockMvc
            .perform(
                get("/api/booking/all")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getAll_FoundWhenBookingsExistInDb() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/all")
            )
            .andExpect(status().isFound());
    }


    @Test
    @WithMockUser(username = "blabla")
    public void getAllOfAUser_IsFoundWhenUserHasBookings() throws Exception {
        when(bookingService.getAllBookingsOfAUser(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/all/blabla")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOfAUser_NotFoundWhenUserHasNoBookingsInDb() throws Exception {
        when(bookingService.getAllBookingsOfAUser(any())).thenThrow();

        this.mockMvc
            .perform(
                get("/api/booking/all/blabla")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOfAUser_IsForbiddenWhenUserTriesToGetBookingsOfAnotherUser() throws Exception {
        this.mockMvc
            .perform(
                get("/api/booking/all/a")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getAllOfAUser_IsFoundWhenAdminTriesToGetBookingsOfAnotherUser() throws Exception {
        when(bookingService.getAllBookingsOfAUser(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/all/a")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void book_ReturnsCreatedWhenPassingCorrectArguments() throws Exception {
        when(bookingService.book(1, 1, "dt", 1, "blabla")).thenReturn(new Booking());
        var request = new ArrayList<String>();
        request.add("1");
        request.add("1");
        request.add("dt");
        request.add("1");
        request.add("blabla");

        var json = new ObjectMapper().writeValueAsString(request);

        this.mockMvc
            .perform(
                post("/api/booking/book").content(json).contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOnOwnedSpotsCurrentUser_IsFoundWhenThereAreBookingsOnUsersSpots() throws Exception {
        when(bookingService.getAllBookingsOfOwnedSpots(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/bookingsOnOwnedSpots")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOnOwnedSpots_IsFound() throws Exception {
        when(bookingService.getAllBookingsOfOwnedSpots(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/bookingsOnOwnedSpots/blabla")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOnOwnedSpots_IsNotFoundWhenNoBookingsFound() throws Exception {
        when(bookingService.getAllBookingsOfOwnedSpots(any())).thenThrow();

        this.mockMvc
            .perform(
                get("/api/booking/bookingsOnOwnedSpots/blabla")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAllOnOwnedSpots_ForbiddenWhenUserTriesToAccessAnotherUsersData() throws Exception {
        when(bookingService.getAllBookingsOfAUser(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/bookingsOnOwnedSpots/aa")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getAllOnOwnedSpots_IsFoundWhenAdminTriesToAccessAnotherUsersData() throws Exception {
        when(bookingService.getAllBookingsOfAUser(any())).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/bookingsOnOwnedSpots/aa")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getOnlyAvailable_IsFoundWhenThereAreResults() throws Exception {
        when(bookingService.getOnlyAvailableParkingspotsInAParkinglot(1,"a", 1, TYPE.Regular)).thenReturn(List.of(new Parkingspot()));

        this.mockMvc
            .perform(
                get("/api/booking/onlyavailable/1/Regular/a/1")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getOnlyAvailable_IsNotFoundWhenZeroResults() throws Exception {
        when(bookingService.getOnlyAvailableParkingspotsInAParkinglot(1,"a", 1, TYPE.Regular)).thenThrow();

        this.mockMvc
            .perform(
                get("/api/booking/onlyavailable/1/Regular/a/1")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void get_IsFound() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin("Administrator", 2, 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "Administrator"});

            var user = new User();
            user.setId(2);
            var booking = new Booking();
            booking.setId(1);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            booking.setUser(user);
            booking.setParkinglot(parkinglot);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(bookingService.getBookingById(1)).thenReturn(booking);

            this.mockMvc
                .perform(
                    get("/api/booking/get/1")
                )
                .andExpect(status().isFound());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_NotAuthorizedWhenAuthorizationFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin("User", 2, 2, 2))
                .thenReturn(false);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "User"});

            var user = new User();
            user.setId(2);
            var booking = new Booking();
            booking.setId(1);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            booking.setUser(user);
            booking.setParkinglot(parkinglot);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(bookingService.getBookingById(1)).thenReturn(booking);

            this.mockMvc
                .perform(
                    get("/api/booking/get/1")
                )
                .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void deleteBooking_NoContentWhenDeletionOk() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin("Administrator", 2, 2, 2))
                .thenReturn(true);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "Administrator"});

            var user = new User();
            user.setId(2);
            var booking = new Booking();
            booking.setId(1);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            booking.setUser(user);
            booking.setParkinglot(parkinglot);

            when(userService.getByUsername("blabla")).thenReturn(user);
            when(bookingService.getBookingById(1)).thenReturn(booking);
            when(deletionService.deleteBooking(1)).thenReturn(true);

            this.mockMvc
                .perform(
                    delete("/api/booking/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isNoContent());
        }
    }

    @Test
    @WithMockUser(username = "blabla")
    public void deleteBooking_NotAuthorizedWhenAuthorizationFails() throws Exception {
        try (MockedStatic<AuthorizationHelper> authorizationHelperMockedStatic = Mockito.mockStatic(AuthorizationHelper.class)) {
            authorizationHelperMockedStatic.when(() -> AuthorizationHelper.currentUserOrParkinglotOwnerOrAdmin("User", 2, 2, 2))
                .thenReturn(false);

            authorizationHelperMockedStatic.when(AuthorizationHelper::getCurrentUserInfo)
                .thenReturn(new String[] {"blabla", "User"});

            var user = new User();
            user.setId(2);
            var booking = new Booking();
            booking.setId(1);
            var parkinglot = new Parkinglot();
            parkinglot.setOwner(user);
            booking.setUser(user);
            booking.setParkinglot(parkinglot);

            when(userService.getByUsername("blabla")  ).thenReturn(user);
            when(bookingService.getBookingById(1)).thenReturn(booking);
            when(deletionService.deleteBooking(1)).thenReturn(true);

            this.mockMvc
                .perform(
                    delete("/api/booking/delete/1")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
        }
    }
}
