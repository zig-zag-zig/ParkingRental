package hiof.parking.helpers;

import hiof.parking.model.Parkinglot;
import hiof.parking.model.Parkingspot;

import java.util.Date;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static hiof.parking.helpers.DateCheckerHelper.dateFormat;

public class LotAndSpotHelper {
    public static long getScheduleSizeOfSpotsInLot(Parkinglot parkinglot) {
        var size = parkinglot.getSpots().size();
        if (size == 0)
            return 365 * 25;
        else
            return parkinglot.getSpots().get(0).getSchedule().size();
    }

    public static void createScheduleForNewSpot(Parkinglot parkinglot, Parkingspot parkingspot, Date now) {
        var scheduleSizeOfLot = getScheduleSizeOfSpotsInLot(parkinglot);

        for (int hours = 0; hours < scheduleSizeOfLot; hours++) {
            var dateAndTimeToAddToSchedule = DateCheckerHelper.addHoursToAnyTime(now, hours);
            parkingspot.getSchedule().put(DateCheckerHelper.dateFormat.format(dateAndTimeToAddToSchedule), true);
        }
    }
}
