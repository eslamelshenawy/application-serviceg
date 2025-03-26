package gov.saip.applicationservice.filters;


import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String cookie = request.getHeader("Cookie");
        String token = null;
        String userName = null;
//        log.info("Request Url iss ==>> {}" , request.getRequestURI());

        if (authorization != null && authorization.startsWith(Constants.TOKEN_TYPE)) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
            response.addHeader("user", userName);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtility.validateToken(token)) {
                UserDetails userDetails = new User(userName, "", jwtUtility.getAuthorities(token));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                HashMap<String, Object> basicUserinfo = new HashMap<String, Object>();
                basicUserinfo.put("request", request);
                basicUserinfo.put("cookie", cookie);
                jwtUtility.setBasicUserInfo(token, basicUserinfo);

                usernamePasswordAuthenticationToken.setDetails(basicUserinfo);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }
}
