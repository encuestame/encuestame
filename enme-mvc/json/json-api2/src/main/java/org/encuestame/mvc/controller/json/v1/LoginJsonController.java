package org.encuestame.mvc.controller.json.v1;


import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/login.json")
public class LoginJsonController extends AbstractJsonControllerV1 {

    /**
     *
     */
    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public LoginStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            return new LoginStatus(true, auth.getName());
        } else {
            return new LoginStatus(false, null);
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public LoginStatus login(
            @RequestParam("j_username") String username,
            @RequestParam("j_password") String password) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                filterValue(username),
                filterValue(password));
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new LoginStatus(auth.isAuthenticated(), auth.getName());
        } catch (BadCredentialsException e) {
            return new LoginStatus(false, null);
        }
    }

    /**
     *
     * @author
     */
    public class LoginStatus {

        private final boolean loggedIn;
        private final String username;

        public LoginStatus(boolean loggedIn, String username) {
            this.loggedIn = loggedIn;
            this.username = username;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public String getUsername() {
            return username;
        }
    }

}
