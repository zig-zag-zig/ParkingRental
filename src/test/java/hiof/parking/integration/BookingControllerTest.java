/*
package hiof.parking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hiof.parking.controller.BookingController;
import hiof.parking.model.Booking;
import hiof.parking.service.*;
import hiof.parking.service.interfaces.IDeletionServiceBooking;
import hiof.parking.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @MockBean
    private BookingService bookingService;
    @MockBean
    private IDeletionServiceBooking deletionService;
    @MockBean
    private IUserService userService;
    */
/*@Autowired
    private ParkinglotService parkinglotService;
    @Autowired
    private ParkingspotService parkingspotService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeletionService deletionService;*//*

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getReqBadRequestWhenNoBookingsInDb() throws Exception {
        this.mockMvc
            .perform(
                get("/api/booking/all")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getRequestFound() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of(new Booking()));

        this.mockMvc
            .perform(
                get("/api/booking/all")
            )
            .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void postReq() throws Exception {
        when(bookingService.book(1, 1, "dt", 1, "blabla")).thenReturn(new Booking());
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

    */
/*@Test
    @WithMockUser(username = "a", authorities = "Administrator")
    public void putReq() throws Exception {
        var user = userService.createUser("a", "a", "a", "First", "Usah", "Home", "ff", 2, 1001, "dd");
        var lot = parkinglotService.createParkingLot(new Location("Testistad", "Test road", 23, 1321, "Fake"), user.getUsername());
        var spot = parkingspotService.createParkingspot(lot.getId(), TYPE.Regular, 50);
        var booking = bookingService.book(spot.getId(), lot.getId(), dateFormat.format(DateCheckerHelper.addHoursToAnyTime(new Date(), 3)), 2, user.getUsername());
        System.out.println(bookingService.getAllBookings());
        this.mockMvc
            .perform(
                delete("/api/booking/delete/" + booking.getId())
                    .with(csrf())
            )
            .andExpect(status().isNoContent());

        deletionService.deleteBooking(booking.getId());
        deletionService.deleteParkingspot(spot.getId());
        deletionService.deleteParkinglot(lot.getId());
        deletionService.deleteUser(user.getUsername());
    }*//*

}
*/
