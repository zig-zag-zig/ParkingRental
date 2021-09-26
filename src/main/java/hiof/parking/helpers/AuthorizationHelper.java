package hiof.parking.helpers;

import hiof.parking.model.ROLE;
import hiof.parking.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class AuthorizationHelper {
    public static boolean currentUserOrParkinglotOwnerOrAdmin(String roleOfCurrentUser, long currentUserId, long ownerId, long idOfOwnerOfObject) {
        if (roleOfCurrentUser.equalsIgnoreCase(ROLE.Administrator.toString()) || idOfOwnerOfObject == currentUserId || ownerId == currentUserId)
            return true;
        else
            return false;
    }

    public static boolean currentUserOrAdmin(String roleOfCurrentUser, long currentUserId, long idOfOwnerOfObject) {
        if (roleOfCurrentUser.equalsIgnoreCase(ROLE.Administrator.toString()) || idOfOwnerOfObject == currentUserId)
            return true;
        else
            return false;
    }

    public static String[] getCurrentUserInfo() {
        String[] userInfoArray = new String[2];
        var usernameOfCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var roles = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        var roleOfCurrentUser = roles.toArray()[0].toString();
        userInfoArray[0] = usernameOfCurrentUser;
        userInfoArray[1] = roleOfCurrentUser;
        return userInfoArray;
    }
}
