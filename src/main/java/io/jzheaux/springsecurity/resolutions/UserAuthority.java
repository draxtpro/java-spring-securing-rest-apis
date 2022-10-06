package io.jzheaux.springsecurity.resolutions;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "authorities")
public class UserAuthority {

    @Id
    private
    UUID id;

    @Column
    private String authority;

    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private User user;

    UserAuthority() {}

    UserAuthority(User user, String authority) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.authority = authority;
    }

    String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
