package com.pehlivan.security.services;

import com.pehlivan.security.model.User;
import com.pehlivan.security.repository.UserRepository;
import com.pehlivan.security.security.adapters.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return new SecurityUser(user);
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

    public void unlockUserAccount(String username){
        var user = getByUserName(username);
        user.setFailedLoginAttempt(0);
        save(user);
    }

    public void lockUserAccount(String username) {
        var user = getByUserName(username);
        user.lock();
        save(user);
    }
}
