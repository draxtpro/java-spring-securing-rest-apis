package io.jzheaux.springsecurity.resolutions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "users")
public class User implements Serializable {

    @Id
    private UUID id;

    private String username;

    private String password;

    protected boolean enabled = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Collection<UserAuthority> userAuthorities = new ArrayList<>();


    public User() {
    }

    public User(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        this.userAuthorities = user.userAuthorities;
    }

    public Collection<UserAuthority> getUserAuthorities() {
        return Collections.unmodifiableCollection(this.userAuthorities);
    }

    public void grantAuthority(String authority) {
        UserAuthority userAuthority = new UserAuthority(this, authority);
        this.userAuthorities.add(userAuthority);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static class UserBuilder {

        private String username;
        private String userPassword;
        private List<String> authorities;

        public UserBuilder(String username, String userPassword) {
            this.username= username;
            this.userPassword= userPassword;
            authorities.clear();
        }

        public UserBuilder grantAuthority(String authority){
            this.authorities.add(authority);
            return this;
        }

        public User build(){
            User builtUser = new User(this.username, this.userPassword);
            this.authorities.forEach(builtUser::grantAuthority);
            return builtUser;
        }
    }
}
