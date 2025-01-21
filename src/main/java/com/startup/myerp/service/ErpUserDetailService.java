package com.startup.myerp.service;

import com.startup.myerp.repository.ErpUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ErpUserDetailService implements UserDetailsService {

    private final ErpUserRepository erpUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return erpUserRepository.findByUserName(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found: " + username));
    }
}
