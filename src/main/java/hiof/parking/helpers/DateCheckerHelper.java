package hiof.parking.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCheckerHelper {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");

    //adds (int hours)Xamount of hours to the time of a date
    public static Date addHoursToAnyTime(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hours);
        return c.getTime();
    }

    //checks if dates are in the past or not, returns false if they're in the past
    public static boolean dateIsNotInThePast(Date dateAndHour) throws ParseException {
        //parsing the current date and time to a string parsed by dateformatter dateFormat
        String nowparsed = dateFormat.format(new Date());

        //if the bookingdate isn't in the past, to avoid seing/booking expired timeslots
        if (dateAndHour.before(dateFormat.parse(nowparsed)))
            return false;
        else
            return true;
    }

    public static boolean dateIsNotTooFarInTheFuture(Date dateAndHour, String lastScheduleDate) throws ParseException {
        if (dateAndHour.after(dateFormat.parse(lastScheduleDate)))
            return false;
        else
            return true;
    }

    public static void dateIsInRange(Date dateAndHour, String lastScheduleDate) throws Exception {
        if (dateIsNotInThePast(dateAndHour) && dateIsNotTooFarInTheFuture(dateAndHour, lastScheduleDate))
            return;
        else
            throw new Exception("Dates are not in range!");
    }
}
