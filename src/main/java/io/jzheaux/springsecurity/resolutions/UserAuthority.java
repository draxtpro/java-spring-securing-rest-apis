package io.jzheaux.springsecurity.resolutions;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "authorities")
public class UserAuthority {

    @Id
    private
    UUID id;

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
