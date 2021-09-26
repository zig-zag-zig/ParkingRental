package hiof.parking;

import hiof.parking.model.Location;
import hiof.parking.model.ROLE;
import hiof.parking.model.User;
import hiof.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }

}

/*@SpringBootApplication
public class ParkingApplication extends SpringBootServletInitializer implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ParkingApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParkingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        *//*try {
            userService.getAllUsers();

        } catch (IllegalArgumentException e) {
            userService.createUser("a", "a", "a", "first", "admin", "a", "s", 2, 3242, "sda");
            userService.createUser("aa", "a", "a","second", "user", "f", "x", 5, 1123, "asda");

        }*//*
    }
}*/
