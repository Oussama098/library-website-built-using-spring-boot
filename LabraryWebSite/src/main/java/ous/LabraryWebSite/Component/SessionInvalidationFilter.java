package ous.LabraryWebSite.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionInvalidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String loginPageUrl = request.getContextPath() + "/login"; // Adjust for your login page URL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and is trying to access the login page
        if (auth != null && auth.isAuthenticated() && loginPageUrl.equals(request.getRequestURI())) {
            // Invalidate session and log the user out
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        filterChain.doFilter(request, response);
    }

}
