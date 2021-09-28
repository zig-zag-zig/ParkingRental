package hiof.parking.helpers;

import hiof.parking.model.Parkinglot;
import hiof.parking.model.Parkingspot;

import java.util.Date;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class LotAndSpotHelper {
    public static long getScheduleSizeOfSpotsInLot(Parkinglot parkinglot) {
        if (parkinglot.getSpots().isEmpty())
            return 365 * 24;
        else {
            removeOldDatesFromSchedule(parkinglot);
            return getAmountOfHoursToAddToSchedule(parkinglot);
        }
    }

    public static void removeOldDatesFromSchedule(Parkinglot parkinglot) {
        for (var spot : parkinglot.getSpots()) {
            for (var entry : spot.getSchedule().entrySet()) {
                var timeFromSchedule = entry.getKey();
                var nowString = DateCheckerHelper.dateFormat.format(new Date());
                try {
                    var now = DateCheckerHelper.dateFormat.parse(nowString);
                    var timeFromScheduleParsed = DateCheckerHelper.dateFormat.parse(timeFromSchedule);

                    if (timeFromScheduleParsed.before(now)) {
                        spot.getSchedule().remove(entry.getKey());
                    } else
                        break;

                } catch (Exception ignored) {
                }
            }
        }
    }

    public static int getAmountOfHoursToAddToSchedule(Parkinglot parkinglot) {
        var size = 0;
        for (var entry : parkinglot.getSpots().get(0).getSchedule().entrySet()) {
            var timeFromSchedule = entry.getKey();
            var nowString = DateCheckerHelper.dateFormat.format(new Date());
            try {
                var now = DateCheckerHelper.dateFormat.parse(nowString);
                var timeFromScheduleParsed = DateCheckerHelper.dateFormat.parse(timeFromSchedule);

                if (!timeFromScheduleParsed.before(now)) {
                    size++;
                }

            } catch (Exception ignored) {}
        }
        return size;
    }

    public static void createScheduleForNewSpot(Parkinglot parkinglot, Parkingspot parkingspot) {
        var scheduleSizeOfLot = getScheduleSizeOfSpotsInLot(parkinglot);
        var fromDate = new Date();

        for (int hours = 0; hours < scheduleSizeOfLot; hours++) {
            var dateAndTimeToAddToSchedule = DateCheckerHelper.addHoursToAnyTime(fromDate, hours);
            parkingspot.getSchedule().put(DateCheckerHelper.dateFormat.format(dateAndTimeToAddToSchedule), true);
        }
    }
}
