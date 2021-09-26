/*
package hiof.parking.integration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkingspotControllerTest {
    private TestFactoryController tf;
    private int port = 5678;
    private Javalin app = null;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryController(port, app);

        HttpResponse response = Unirest.post("http://localhost:"+port+"/lot/1/update/create/1/")
            .field("type", "regular")
            .asString();

        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/lot/1/update/create/1/")
            .asString();

        assertEquals(404, response.getStatus());
    }

    @Test
    void getSpot() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/parkingspot/1/1").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/parkingspot/1/100").asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    void update() throws Exception {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/lot/1/update/spot/1/1/")
            .field("type", "handicap")
            .asString();

        assertEquals(302, response.getStatus());
    }


    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/parkingspot/1/delete/1").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/parkingspot/1/delete/1").asString();
        assertEquals(404, response.getStatus());

        tf.quit(app);
    }
}
*/
