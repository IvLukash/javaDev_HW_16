package goit.edu.webheloer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
public class UserCookie {
    private static final String USER_ID = "user_id";
    @Value("${cookie.max-age}")
    private Duration cookieMaxAge;

    public void createUserId(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (USER_ID.equals(cookie.getName())) {
                    return;
                }
            }
        }

        UUID userId = UUID.randomUUID();
        Cookie cookie = new Cookie(USER_ID, userId.toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) cookieMaxAge.getSeconds());
        response.addCookie(cookie);
    }
}
