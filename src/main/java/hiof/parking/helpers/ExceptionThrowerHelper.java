package hiof.parking.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionThrowerHelper {
    public static Exception throwCorrectException(Exception e) {
        if (e instanceof ResponseStatusException) {
            if (((ResponseStatusException) e).getStatus() == HttpStatus.FORBIDDEN || ((ResponseStatusException) e).getStatus() == HttpStatus.BAD_REQUEST)
                return e;
        }
        return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
}
