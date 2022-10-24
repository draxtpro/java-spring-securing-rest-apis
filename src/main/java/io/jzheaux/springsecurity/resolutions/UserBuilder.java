package io.jzheaux.springsecurity.resolutions;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    private final String username;
    private final String userPassword;
    private String fullName;
    private final List<String> authorities;

    public UserBuilder(String username, String userPassword) {
        this.username = username;
        this.userPassword = userPassword;
        authorities = new ArrayList<>();
    }

    public UserBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserBuilder withGrantedAuthority(String authority) {
        authorities.add(authority);
        return this;
    }

    public User build() {
        User builtUser = new User(this.username, this.userPassword);
        builtUser.setFullName(this.fullName);
        this.authorities.forEach(builtUser::grantAuthority);
        return builtUser;
    }
}
