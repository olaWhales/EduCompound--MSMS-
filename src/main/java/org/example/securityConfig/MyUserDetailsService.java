package org.example.securityConfig;

import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

//        i added this check for user acive status
        // 🔐 REQUIRED CHECKS
        if (!user.isVerified()) {throw new DisabledException("Email not verified.");}
        if (!user.isActive()) {throw new DisabledException("Account awaiting admin activation.");}


        return new UserPrincipal(user);
    }
}

