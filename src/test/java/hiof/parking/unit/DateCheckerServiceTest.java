package hiof.parking.unit;

import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.util.Date;

import static hiof.parking.helpers.DateCheckerHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateCheckerServiceTest {
    private Date parsedDate;
    private Date twentyFourHoursFromNow;
    private Date tooFar;
    private String twentyFourHoursFromNowStr;
    private String tooFarStr;

    @BeforeAll
    void addHoursToAnyTimeTest() throws ParseException {
        parsedDate = dateFormat.parse(dateFormat.format(new Date()));

        twentyFourHoursFromNow = addHoursToAnyTime(parsedDate, 24);

        long diffMilliSeconds = twentyFourHoursFromNow.getTime() - parsedDate.getTime();
        long diffHours = diffMilliSeconds / (60 * 60 * 1000);

        assertEquals(24, diffHours);
    }

    @Test
    void dateIsNotInThePastTest() throws ParseException {
        Date old = addHoursToAnyTime(parsedDate, -2);
        boolean answer = dateIsNotInThePast(old);
        assertFalse(answer);

        answer = dateIsNotInThePast(twentyFourHoursFromNow);
        assertTrue(answer);
    }

    @Test
    void dateIsNotTooFarInTheFutureTest() throws ParseException {
        tooFar = addHoursToAnyTime(parsedDate, 25);
        twentyFourHoursFromNowStr = dateFormat.format(twentyFourHoursFromNow);

        boolean answer = dateIsNotTooFarInTheFuture(tooFar, twentyFourHoursFromNowStr);
        assertFalse(answer);

        tooFarStr = dateFormat.format(tooFar);

        answer = dateIsNotTooFarInTheFuture(twentyFourHoursFromNow, tooFarStr);
        assertTrue(answer);
    }

    @AfterAll
    void dateIsInRangeTest() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            dateIsInRange(tooFar, twentyFourHoursFromNowStr);
        });

        dateIsInRange(twentyFourHoursFromNow, tooFarStr);
    }
}
