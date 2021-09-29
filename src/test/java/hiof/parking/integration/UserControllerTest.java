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
public class UserControllerTest {
    @MockBean
    private IDeletionService deletionService;
    @MockBean
    private IUserService userService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void create_ReturnsCreatedWhenPassingCorrectArguments() throws Exception {
        var request = new ArrayList<String>();
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("1");
        request.add("1");
        request.add("a");

        var json = new ObjectMapper().writeValueAsString(request);

        when(userService.createUser("a", "a", "a", "a", "a", "a", "a", 1, 1, "a")).thenReturn(new User());

        this.mockMvc
            .perform(
                post("/api/user/create").content(json).contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void update_NoContentWhenOk() throws Exception {
        var request = new ArrayList<String>();
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("a");
        request.add("1");
        request.add("1");
        request.add("a");

        var json = new ObjectMapper().writeValueAsString(request);

        when(userService.updateUser("blabla", "a", "a", "a", "a", 1, 1, "a")).thenReturn(new User());

        this.mockMvc
            .perform(
                put("/api/user/update/blabla").content(json).contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void delete_NoContentWhenOk() throws Exception {
        when(deletionService.deleteUser("blabla")).thenReturn(true);

        this.mockMvc
            .perform(
                delete("/api/user/delete/blabla")
                    .with(csrf())
            )
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void delete_BadRequestWhenFailingToDeleteUser() throws Exception {
        when(deletionService.deleteUser("blabla")).thenReturn(false);

        this.mockMvc
            .perform(
                delete("/api/user/delete/blabla")
                    .with(csrf())
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void delete_ForbiddenWhenUserTriesToDeleteAnotherUser() throws Exception {
        this.mockMvc
            .perform(
                delete("/api/user/delete/a")
                    .with(csrf())
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void delete_NoContentWhenAdminTriesToDeleteAnotherUser() throws Exception {
        when(deletionService.deleteUser("a")).thenReturn(true);

        this.mockMvc
            .perform(
                delete("/api/user/delete/a")
                    .with(csrf())
            )
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_FoundWhenUserFound() throws Exception {
        when(userService.getByUsername(any())).thenReturn(new User());

        this.mockMvc
            .perform(
                get("/api/user/get/blabla")
            )
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void get_NotFoundWhenUserNotInDb() throws Exception {
        when(userService.getByUsername(any())).thenThrow();

        this.mockMvc
            .perform(
                get("/api/user/get/blabla")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getCurrentuser_FoundWhenUserInDb() throws Exception {
        when(userService.getByUsername(any())).thenReturn(new User());

        this.mockMvc
            .perform(
                get("/api/user/get")
            )
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getCurrentuser_NotFoundWhenUserNotInDb() throws Exception {
        when(userService.getByUsername(any())).thenThrow();

        this.mockMvc
            .perform(
                get("/api/user/get")
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Administrator", username = "blabla")
    public void getAll_FoundWhenAdmin() throws Exception {
        when(userService.getByUsername(any())).thenReturn(new User());

        this.mockMvc
            .perform(
                get("/api/user/all")
            )
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "blabla")
    public void getAll_ForbiddenWhenUser() throws Exception {
        this.mockMvc
            .perform(
                get("/api/user/all")
            )
            .andExpect(status().isForbidden());
    }
}