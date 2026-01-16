package org.example.securityConfig;//package org.example.securityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.securityConfig.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

//@Component
//@Slf4j
//public class JwtFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtService service;
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            try {
//                username = service.extractUsername(token);
//            } catch (Exception e) {
//                System.out.println("Failed to extract username from token:" + e.getMessage());
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (service.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//                System.out.println("Authenticated allUsers: {} with authorities: " + username + userDetails.getAuthorities());
//            } else {
//                System.out.println("Invalid token for allUsers " + username);
////                log.warn("Invalid token for allUsers: {}", username);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
            } catch (Exception e) {
                log.warn("Invalid JWT token: {}", e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 1️⃣ Load user entity (NOT just UserDetails)
            Users user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 2️⃣ Block deactivated users immediately
            if (!user.isActive()) {
                throw new DisabledException("User account is deactivated");
            }

            // 3️⃣ Compare token issued-at with statusUpdatedAt
////            Instant tokenIssuedAt =
////                    jwtService.extractAllClaims(token).getIssuedAt().toInstant();
//
//            if (tokenIssuedAt.isBefore(user.getStatusUpdatedAt())) {
//                throw new DisabledException("JWT is no longer valid");
//            }

            Instant tokenIssuedAt = jwtService.extractIssuedAt(token);

            if (tokenIssuedAt.isBefore(user.getStatusUpdatedAt())) {
                throw new DisabledException("JWT is no longer valid");
            }


            // 4️⃣ Load Spring Security user
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // 5️⃣ Final JWT validation
            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
