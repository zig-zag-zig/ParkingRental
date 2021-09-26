/*
package hiof.parking.integration;

import Helpers.CurrentUser;
import io.javalin.Javalin;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    private TestFactoryController tf;
    private int port = 1234;
    private Javalin app = null;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryController(port, app);

        HttpResponse response = Unirest.post("http://localhost:"+port+"/users/create/new/user/api/create")
            .field("firstname", "aaa")
            .field("surname", "bbbb")
            .field("city", "sss")
            .field("address", "cccc")
            .field("zipcode", "1111")
            .field("number", "123")
            .field("area", "ar")
            .field("phone", "")
            .asString();
        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/users/create/new/user/api/create")
            .field("firstname", "aaa")
            .field("address", "cccc")
            .field("phone", "")
            .asString();
        assertEquals(404, response.getStatus());

        CurrentUser.loggedInAs = tf.user;
    }

    @Test
    void getAll() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/users").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void getById() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/users/10000").asString();
        assertEquals(404, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/users/1").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void update() throws Exception {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/users/1/api/update/")
            .field("firstname", "ffff")
            .field("surname", "aaasss")
            .field("city", "sss")
            .field("address", "cccc")
            .field("zipcode", "4353")
            .field("number", "3")
            .field("area", "ar").asString();
        assertEquals(302, response.getStatus());
    }

    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/users/delete/1").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/users/delete/1").asString();
        assertEquals(404, response.getStatus());

        tf.quit(app);
    }
}
*/
