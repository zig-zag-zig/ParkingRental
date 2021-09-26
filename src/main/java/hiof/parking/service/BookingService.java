package hiof.parking.service;

import hiof.parking.helpers.DateCheckerHelper;
import hiof.parking.model.*;
import hiof.parking.repository.BookingRepo;
import hiof.parking.repository.ParkinglotRepo;
import hiof.parking.repository.ParkingspotRepo;
import hiof.parking.repository.UserRepo;
import hiof.parking.service.interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicInteger;

import static hiof.parking.helpers.DateCheckerHelper.*;

@Service
public class BookingService implements IBookingService {
    private BookingRepo bookingRepo;
    private ParkingspotRepo parkingspotRepo;
    private ParkinglotRepo parkinglotRepo;
    private UserRepo userRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo, ParkingspotRepo parkingspotRepo, ParkinglotRepo parkinglotRepo, UserRepo userRepo) {
        this.bookingRepo = bookingRepo;
        this.parkingspotRepo = parkingspotRepo;
        this.parkinglotRepo = parkinglotRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Booking getBookingById(long bookingId) {
        return bookingRepo.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    @Override
    public List<Booking> getAllBookings() {
        var all = bookingRepo.findAll();

        if (all.size() == 0)
            throw new IllegalArgumentException("None found!");

        return all;
    }

    @Override
    public List<Booking> getAllBookingsOfAUser(String username) {
        var all = bookingRepo.findAll();

        if (all.size() == 0)
            throw new IllegalArgumentException("None found!");

        var bookingsOfUser = new ArrayList<Booking>();
        for (var booking : all) {
            if (booking.getUser().getUsername().equalsIgnoreCase(username))
                bookingsOfUser.add(booking);
        }
        return bookingsOfUser;
    }

    //gets all the bookings of the user's parkingspots
    @Override
    public List<Booking> getAllBookingsOfOwnedSpots(String username) {
        var all = bookingRepo.findAll();

        if (all.size() == 0)
            throw new IllegalArgumentException("None found!");

        var result = new ArrayList<Booking>();
        for (var booking: all) {
            if (booking.getParkinglot().getOwner().getUsername().equalsIgnoreCase(username))
                result.add(booking);
        }

        if (result.size() == 0)
            throw new IllegalArgumentException("No bookings on the parkingspots of the user found!");

        return result;
    }

    @Override
    public Booking book(long spotId, long parkinglotId, String bkdate, int hours, String username) throws Exception {
        var spot =  parkingspotRepo.findById(spotId).orElseThrow(() -> new IllegalArgumentException("Spot not found"));
        var user  =  userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        var schedule = spot.getSchedule();

        int hoursBooked = 0;

        //minimum one hour
        if (hours < 1)
            hours = 1;

        ArrayList<String> availableDates = new ArrayList<>();
        ArrayList<String> unavailableDates = new ArrayList<>();

        Date bookingdate;

        //loop for as many times as the amount of hours wished to book
        for (int k = 0; k < hours; k++) {
            //adds k amount of hours to the current time
            bookingdate = addHoursToAnyTime(dateFormat.parse(bkdate), k);

            //uses method in DateCheckerHelper to make sure the date and time isn't too old or after the last date in the schedule
            dateIsInRange(bookingdate, schedule.lastKey());

            //format the date to a string
            String date = dateFormat.format(bookingdate);

            hoursBooked = bookIfAvailable(schedule, hoursBooked, availableDates, unavailableDates, spot, date);
        }
        someUnavail(spot, schedule, availableDates, unavailableDates);

        parkingspotRepo.save(spot);

        return bookingRepo.save(bookAndReturnNewBooking(user, parkinglotId, (spot.getHourlyPrice() * hoursBooked), availableDates, spot));
    }

    private int bookIfAvailable(SortedMap<String, Boolean> schedule, int hoursBooked, ArrayList<String> availableDates, ArrayList<String> unavailableDates, Parkingspot spot, String date) {
        //if the date and time of the schedule for the parkingspot matches the booking request date
        if (schedule.containsKey(date)) {
            //if the spot is available
            if (schedule.get(date)) {
                //set it to unavaiable
                schedule.put(date, false);
                //increase the hours booked counter
                hoursBooked++;
                //add the dates to the ArrayList with all the spotids of the booking
                availableDates.add(date);
            } else
                unavailableDates.add(date);
        }
        return hoursBooked;
    }

    private void someUnavail(Parkingspot parkingspot, SortedMap<String, Boolean> schedule, ArrayList<String> availableDates, ArrayList<String> unavailableDates) throws Exception {
        if (unavailableDates.isEmpty() == false) {
            if (availableDates.isEmpty() == false) {
                for (String dates : availableDates) {
                    schedule.put(dates, true);
                }
            }
            parkingspotRepo.save(parkingspot);
            throw new Exception("Spot unavaialble at " + unavailableDates);
        }
    }

    private Booking bookAndReturnNewBooking(User user, long parkinglotid, int price, ArrayList<String> availableDates, Parkingspot spot) {
        var booking = new Booking(spot, availableDates, parkinglotRepo.getById(parkinglotid), user, price);
        //put the booking inside the ConcurrentSkipListMap bookings
        var newBooking = bookingRepo.save(booking);
        return newBooking;
    }

    @Override
    public void writeBookingsToCsv(File out) {
        var allBookings = bookingRepo.findAll();
        //will be used to store the total amount of money received in total for all the bookings
        int totalPrice = 0;
        //will be used to store the total amount of orders
        int amountOfBookings = 0;
        //will be used as to contain the string that will be written to the csv
        String joiner;
        try (FileWriter fileWriter = new FileWriter(out)) {
            //for loop over all the bookings
            for (var booking : allBookings) {
                //add all those values to the string that will be written to the csv file
                joiner = booking.getId() + "," + booking.getUser().getId() + "," + booking.getUser().getFirstname() + "," + booking.getUser().getSurname() + "," + booking.getSpot().getId() + ",";
                //dateAndTime is an arraylist, so loop over and print all the elements with a comma between them
                joiner = writeDateAndTimeFromArrayList(joiner, booking);
                //add the rest of the values of the booking to the string
                joiner += booking.getParkinglot().getId() + "," + booking.getParkinglot().getLocation().getCity() + "," + booking.getParkinglot().getLocation().getAddress() + "," + booking.getParkinglot().getLocation().getNumber() + "," + booking.getParkinglot().getLocation().getNumber() + "," + booking.getParkinglot().getLocation().getZipcode() + "," + booking.getParkinglot().getLocation().getArea() + "," + booking.getPrice() + "\n";
                //add the price of each booking to the total
                totalPrice += booking.getPrice();
                //increase the amount of bookings by one
                amountOfBookings++;
                //write the string to the csv
                fileWriter.write(joiner);
            }
            totalBookings(totalPrice, amountOfBookings, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeDateAndTimeFromArrayList(String joiner, Booking booking) {
        var bookingDataAndTime = booking.getDateAndTime();
        for (int j = 0; j < bookingDataAndTime.size(); j++) {
            joiner += bookingDataAndTime.get(j) + ",";
        }
        return joiner;
    }

    private void totalBookings(int totalPrice, int amountOfBookings, FileWriter fileWriter) throws IOException {
        String joiner;
        joiner = amountOfBookings + "," + totalPrice;
        fileWriter.write(joiner);
    }

    @Override
    public List<Parkingspot> getOnlyAvailableParkingspotsInAParkinglot(long parkinglotid, String dateAndTime, int hours, TYPE type) throws Exception {
        ArrayList<String> listOfDates = new ArrayList<>();
        List<Parkingspot> availableParkingspots = new ArrayList<>();

        var parkinglot = parkinglotRepo.findById(parkinglotid).orElseThrow(() -> new IllegalArgumentException("Parkinglot doesn't exist"));

        getListOfDates(parkinglotid, dateAndTime, hours, listOfDates);

        typeChecker(availableParkingspots, type, listOfDates, parkinglot);

        if (availableParkingspots.isEmpty())
            throw new IllegalArgumentException("No available spots found!");

        return availableParkingspots;
    }

    private void getListOfDates(long parkinglotid, String dateAndTime, int hours, ArrayList<String> listOfDates) throws Exception {
        Date searchDates;
        //loop for as many hours as necessary
        for (int i = 0; i < hours; i++) {
            searchDates = getSearchDates(dateAndTime, i);

            //uses method in DateCheckerHelper to make sure the date and time aren't too old or after the last date in the schedule
            DateCheckerHelper.dateIsInRange(searchDates, parkinglotRepo.getById(parkinglotid).getSpots().get(0).getSchedule().lastKey());

            //create a formatted string from the date
            String date = dateFormat.format(searchDates);
            //add it to the list with dates and times to get available spots for
            listOfDates.add(date);
        }
    }

    private Date getSearchDates(String dateAndTime, int i) throws Exception {
        if (i == 0) {
            //no need to add any hours on the first iteration
            return dateFormat.parse(dateAndTime);
        } else {
            //uses method in DateCheckerHelper to return the original time plus the amount of hours added to it
            return DateCheckerHelper.addHoursToAnyTime(dateFormat.parse(dateAndTime), i);
        }
    }

    private void typeChecker(List<Parkingspot> availableParkingspots, TYPE type, ArrayList<String> listOfDates, Parkinglot parkinglot) {
        if (type == null)
            getOnlyAvailAllTypes(availableParkingspots, listOfDates, parkinglot);
        else
            getOnlyAvailOneType(type, availableParkingspots, listOfDates, parkinglot);
    }

    private void getOnlyAvailAllTypes(List<Parkingspot> availableParkingspots, ArrayList<String> listOfDates, Parkinglot parkinglot) {
        for (var spot : parkinglot.getSpots()) {
            findAvailable(availableParkingspots, listOfDates, spot);
        }
    }

    private void getOnlyAvailOneType(TYPE type, List<Parkingspot> availableParkingspots, ArrayList<String> listOfDates, Parkinglot parkinglot) {
        for (var spot : parkinglot.getSpots()) {
            if (spot.getType() == type) {
                findAvailable(availableParkingspots, listOfDates, spot);
            }
        }
    }

    private void findAvailable(List<Parkingspot> availableParkingspots, ArrayList<String> listOfDates, Parkingspot spot) {
        for (AtomicInteger i = new AtomicInteger(0); i.intValue() < listOfDates.size(); i.incrementAndGet()) {
            findDateInTheSchedule(availableParkingspots, listOfDates, spot, i);
        }
    }

    private void findDateInTheSchedule(List<Parkingspot> availableParkingspots, ArrayList<String> listOfDates, Parkingspot spot, AtomicInteger i) {
        if (spot.getSchedule().containsKey(listOfDates.get(i.intValue()))) {
            addSpotToListIfAvailable(availableParkingspots, listOfDates, spot, i);
        }
    }

    private void addSpotToListIfAvailable(List<Parkingspot> availableParkingspots, ArrayList<String> listOfDates, Parkingspot spot, AtomicInteger i) {
        if (spot.getSchedule().get(listOfDates.get(i.intValue()))) {
            if (!availableParkingspots.contains(spot))
                availableParkingspots.add(spot);
        } else {
            availableParkingspots.remove(spot);
            i.set(listOfDates.size());
        }
    }
}
