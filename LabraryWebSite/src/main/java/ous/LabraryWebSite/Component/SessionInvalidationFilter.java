package ous.LabraryWebSite.Component;

import ch.qos.logback.core.spi.FilterReply;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionInvalidationFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the user is accessing the login page
        if (httpRequest.getRequestURI().equals("/login")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // Check if the user is already authenticated
            if (auth != null && auth.isAuthenticated()) {
                // Invalidate the session and log the user out
                new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, auth);
            }
        }

        // Continue with the rest of the filter chain
        chain.doFilter(request, response);
    }

}
