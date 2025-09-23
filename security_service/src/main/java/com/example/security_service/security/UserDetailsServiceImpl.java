package com.example.security_service.security;

import com.example.security_service.entity.Roles;
import com.example.security_service.entity.Users;
import com.example.security_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("user not fount"));

        Set<Roles> roles = users.getRoles();
        List<SimpleGrantedAuthority> list = roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).toList();

        return new User(users.getUserName(),users.getPassword(),list);
    }
}
