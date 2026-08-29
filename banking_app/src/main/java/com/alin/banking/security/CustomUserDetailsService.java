package com.alin.banking.security;

import com.alin.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.alin.banking.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String cnp) throws UsernameNotFoundException {
        User user = userRepository.findByCnp(cnp).orElseThrow(() -> new UsernameNotFoundException("Utilizatorul cu CNP-ul " + cnp + " nu a fost gasit"));
        return new CustomUserDetails(user);
    }
}
