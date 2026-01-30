package goit.edu.webheloer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.UUID;

@Component
public class UserCookieInterceptor implements HandlerInterceptor {
    private static final String USER_ID = "user_id";
    private final Duration cookieMaxAge;

    public UserCookieInterceptor(@Value("${cookie.max-age}") Duration cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        boolean hasUserCookie = false;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (USER_ID.equals(cookie.getName())) {
                    hasUserCookie = true;
                    break;
                }
            }
        }

        if (!hasUserCookie) {
            UUID userId = UUID.randomUUID();
            Cookie cookie = new Cookie(USER_ID, userId.toString());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) cookieMaxAge.getSeconds());
            response.addCookie(cookie);
            response.sendRedirect(request.getRequestURI());
            return false;
        }
        return true;
    }
}
