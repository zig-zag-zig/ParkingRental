/*
package hiof.parking.integration;

import Helpers.DateCheckerHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.Date;
import static model.BookingSchedule.dateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingScheduleControllerTest {
    private TestFactoryController tf;
    private int port = 2345;
    private Javalin app = null;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryController(port, app);

        HttpResponse response = Unirest.get("http://localhost:"+port+"/lot/api/bsch/1/create/1/").asString();

        assertEquals(200, response.getStatus());
    }

    @Test
    void getOnlyAvailable() throws UnirestException {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/bsch/api/result/").asString();
        assertEquals(404, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/bsch/api/1/")
            .field("date", dateFormat.format(DateCheckerHelper.addHoursToAnyTime(new Date(), 1).getTime()))
            .field("hours", "1")
            .field("type", tf.spot.getType())
            .asString();
        assertEquals(302, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/bsch/api/result/").asString();
        assertEquals(200, response.getStatus());
    }


    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/bsch/1/delete").asString();
        assertEquals(200, response.getStatus());

        tf.quit(app);
    }
}
*/
