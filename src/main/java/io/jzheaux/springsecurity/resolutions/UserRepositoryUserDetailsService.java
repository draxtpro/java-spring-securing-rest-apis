package io.jzheaux.springsecurity.resolutions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;

public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private BridgeUser map(User user){
        Collection<GrantedAuthority> authorities = new HashSet<>();

        user.getUserAuthorities().forEach(authority -> {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                authorities.add(new SimpleGrantedAuthority("resolution:read"));
                authorities.add(new SimpleGrantedAuthority("resolution:write"));
            }
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        });
        return new BridgeUser(user, authorities);
    }

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("no user with that username"));
    }

    private static class BridgeUser extends User implements UserDetails {
        private final Collection<GrantedAuthority> authorities;

        public BridgeUser(User user, Collection<GrantedAuthority> authorities) {
            super(user);
            this.authorities= authorities;
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        public boolean isAccountNonExpired() {
            return this.enabled;
        }

        public boolean isAccountNonLocked() {
            return this.enabled;
        }

        public boolean isCredentialsNonExpired() {
            return this.enabled;
        }
    }
}
