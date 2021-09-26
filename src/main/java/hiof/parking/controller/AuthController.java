package hiof.parking.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @GetMapping("/loggedin")
    public boolean isLoggedin() {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return true;
        }
        return false;
    }

    @GetMapping("/admin")
    public boolean isAdmin() {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            var roles = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            var roleOfCurrentUser = roles.toArray()[0].toString();
            if (roleOfCurrentUser.equalsIgnoreCase("Administrator"))
                return true;
        }
        return false;
    }

    @GetMapping("/admin/corretuser/{objectOwnerUsername}")
    public boolean isAdminOrCorrectUser(@PathVariable String objectOwnerUsername) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            var usernameOfCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            var roles = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            var roleOfCurrentUser = roles.toArray()[0].toString();
            if (roleOfCurrentUser.equalsIgnoreCase("Administrator")
                || usernameOfCurrentUser.equalsIgnoreCase(objectOwnerUsername.trim()))
                return true;
        }
        return false;
    }
}
