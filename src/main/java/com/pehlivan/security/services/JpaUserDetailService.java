package com.pehlivan.security.services;

import com.pehlivan.security.model.User;
import com.pehlivan.security.repository.UserRepository;
import com.pehlivan.security.security.adapters.SecurityAuthority;
import com.pehlivan.security.security.adapters.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public SecurityUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        return this.getByUserNameSecurityUser(userName);
    }
    public SecurityUser getByUserNameSecurityUser(String userName) throws UsernameNotFoundException{
        User user = this.getByUserName(userName);
        SecurityUser securityUser = new SecurityUser(user);
        user.getRole()
                .getPermissions()
                .forEach(it -> securityUser.getAuthorities().add(new SecurityAuthority(it.getCode())));
        return securityUser;
    }
    public User getByUserName(String userName)throws UsernameNotFoundException{

        return userRepository
                .findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found! username: "+ userName));
    }
    public User getById(Long id)throws UsernameNotFoundException{

        return userRepository
                .findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found!"));
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }
}
