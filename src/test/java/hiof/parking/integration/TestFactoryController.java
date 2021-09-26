package hiof.parking.integration;/*
package controller;

import Helpers.CurrentUser;
import application.Application;
import io.javalin.Javalin;
import irepository.InterfaceCrud;
import model.*;
import repository.*;
import service.*;
import service.interfaces.*;

public class TestFactoryController {
    InterfaceCrud<User> interfaceCrudUser = new UserRepo();
    InterfaceCrud<Booking> interfaceCrudBooking = new BookingRepo();
    InterfaceCrud<BookingSchedule> interfaceCrudSchedule = new BookingscheduleRepo();
    InterfaceCrud<Parkinglot> interfaceCrudLot = new ParkinglotRepo();
    IUserService iUserService = new UserService(interfaceCrudUser);
    IDeletionService iDeletionService = new DeletionService(interfaceCrudBooking, interfaceCrudSchedule, interfaceCrudUser, interfaceCrudLot);
    IParkinglotService iParkinglotService = new ParkinglotService(interfaceCrudLot, interfaceCrudSchedule);
    IParkinspotService iParkinspotService = new ParkingspotService(interfaceCrudLot, interfaceCrudSchedule);
    IBookingScheduleService iBookingScheduleService = new BookingScheduleService(interfaceCrudSchedule);
    IBookingService iBookingService = new BookingService(interfaceCrudBooking, interfaceCrudUser, interfaceCrudSchedule);
    UserController userController = new UserController(iUserService, iDeletionService);
    BookingController bookingController = new BookingController(iBookingService, iDeletionService);
    ParkinglotController parkinglotController = new ParkinglotController(iParkinglotService, iUserService, iDeletionService);
    BookingScheduleController bookingScheduleController = new BookingScheduleController(iBookingScheduleService, iParkinglotService);
    ParkingspotController parkingspotController = new ParkingspotController(iParkinspotService, iDeletionService);
    LoginController loginController = new LoginController(iUserService);
    User user;
    Parkinglot parkinglot;
    Parkingspot spot;

    public TestFactoryController(int port, Javalin app) throws Exception {
        user = userController.getUserService().createUserService("ss", "ss", "ss", "dd", 19, 1231, "Where");
        CurrentUser.loggedInAs = user;
        parkinglot = parkinglotController.getParkinglotService().createParkingLotService(new Location("f", "o", 4, 4, "ss"), user);
        spot = parkingspotController.getParkinglotService().createParkingspot(parkinglot.getId(), TYPE.Handicap);
        bookingScheduleController.getBookingScheduleService().createForXDays(parkinglot, 1);
        new Application(port, app, bookingScheduleController, bookingController, parkinglotController, parkingspotController, userController, loginController);
    }

    public void quit(Javalin app) {
        if (app != null) {
            app.stop();
        }
    }
}
*/
