package com.app.otp.Service;

import com.app.otp.Entity.User;
import com.app.otp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        User user = repository.findByUserName(phoneNo);
        if(user==null){
            user = new User();
            user.setUserName(phoneNo);
            repository.save(user);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), "",
                new ArrayList<>());
    }
}