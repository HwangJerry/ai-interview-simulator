package soohyunj.interviewsimulator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import soohyunj.interviewsimulator.exception.NotAuthenticatedException;

@Slf4j
public class SecurityUtil {
    public static String getLoginMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
            && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        } else {
            throw new NotAuthenticatedException();
        }
    }
}
