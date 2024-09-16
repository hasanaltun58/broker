package com.broker.Service;

import com.broker.model.CustomUserDetails;
import com.broker.entity.Customer;
import com.broker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        return new CustomUserDetails(customer);
    }
}
