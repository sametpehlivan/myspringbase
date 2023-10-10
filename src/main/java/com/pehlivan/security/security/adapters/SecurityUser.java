package com.pehlivan.security.security.adapters;

import com.pehlivan.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SecurityUser implements UserDetails {
    private final User user;
    private final List<SecurityAuthority> authorities = new ArrayList<>();
    public SecurityUser(User user){
        this.user = user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUserName();
    }
    @Override
    public List<SecurityAuthority> getAuthorities() {
        return authorities;

    }
    //Kullanıcı süresi,süreli bir sistem kullanırsan providerı refactor edebilirsin
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Genellikle hesap üzerinde yapılan uzun vadeli veya kalıcı etkinlik değişikliklerini temsil etmek için kullanılır.
    @Override
    public boolean isEnabled() {
        return true;
    }
    //Bir kullanıcı hesabı "kilitli" olarak işaretlenirse (false), bu hesap geçici bir süre boyunca kilitlenmiş demektir. Kullanıcı, belirli bir süre boyunca hatalı giriş denemeleri nedeniyle kilitlenmiş olabilir.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //Kullanıcının kimlik doğrulama bilgilerinin (şifresinin) süresinin dolup dolmadığını belirten bir boolean değeri döndüren metottur. Eğer kimlik doğrulama bilgileri süresi dolmuşsa (false), kullanıcı girişi engellenebilir.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
