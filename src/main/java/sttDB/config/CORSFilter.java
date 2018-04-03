package sttDB.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter extends OncePerRequestFilter {

    private String allowedHost;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("GET".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        } else {
            setAllowedHost();
            response.setHeader("Access-Control-Allow-Origin", allowedHost);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, experiment");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void setAllowedHost() {
        Environment env = this.getEnvironment();
        for (String profile : env.getActiveProfiles()) {
            if (profile.equalsIgnoreCase("production")) {
                allowedHost = "http://sttdb.udl.cat";
                return;
            }
        }
        allowedHost = "http://localhost:4200";
    }
}