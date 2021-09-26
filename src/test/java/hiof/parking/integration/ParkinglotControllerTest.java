/*
package hiof.parking.integration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkinglotControllerTest {
    private TestFactoryController tf;
    private int port = 3456;
    private Javalin app = null;

    @BeforeAll
    void create() throws Exception {
        tf = new TestFactoryController(port, app);


        HttpResponse response = Unirest.post("http://localhost:"+port+"/lot/create/new/lot/api/create/1")
            .field("city", "b")
            .field("address", "ddd")
            .field("number", "33")
            .field("zipcode", "44")
            .field("area", "ffff")
            .asString();

        assertEquals(302, response.getStatus());

        response = Unirest.post("http://localhost:"+port+"/lot/create/new/lot/api/create/1")
            .field("city", "b")
            .field("address", "ddd")
            .field("number", "33")
            .asString();

        assertEquals(404, response.getStatus());
    }

    @Test
    void getAll() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/lot/").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void getById() throws UnirestException {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/lot/10000").asString();
        assertEquals(404, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/lot/1").asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void update() throws Exception {
        HttpResponse response = Unirest.post("http://localhost:"+port+"/lot/1/update/api/update/1")
            .field("city", "a")
            .field("address", "ssss")
            .field("number", "44")
            .field("zipcode", "6655")
            .field("area", "eeee")
            .asString();
        assertEquals(302, response.getStatus());
    }


    @AfterAll
    void delete() throws Exception {
        HttpResponse response = Unirest.get("http://localhost:"+port+"/api/lot/1/delete/").asString();
        assertEquals(200, response.getStatus());

        response = Unirest.get("http://localhost:"+port+"/api/lot/1/delete/").asString();
        assertEquals(404, response.getStatus());

        tf.quit(app);
    }
}
*/
