package hiof.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
