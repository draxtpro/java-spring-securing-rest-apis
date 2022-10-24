package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
    private static final String USER_PASSWORD = "{bcrypt}$2a$12$.dSqQCYlYTYTOhYMR.LQsOu4cdGn5q8p/LywFIARfmJp6EGG.cEBK";
    private static final String RESOLUTION_READ_AUTHORITY = "resolution:read";
    private static final String RESOLUTION_WRITE_AUTHORITY = "resolution:write";
    private static final String RESOLUTION_ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final ResolutionRepository resolutions;

    private final UserRepository userRepository;

    public ResolutionInitializer(ResolutionRepository resolutions, UserRepository userRepository) {
        this.resolutions = resolutions;
        this.userRepository = userRepository;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.resolutions.save(new Resolution("Read War and Peace", "user"));
        this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
        this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

        User edEditor =
                new UserBuilder("EdEditor", USER_PASSWORD)
                        .withFullName("Ed Schreiberlesling")
                        .withGrantedAuthority(RESOLUTION_READ_AUTHORITY)
                        .withGrantedAuthority(RESOLUTION_WRITE_AUTHORITY).build();

        User markEditor =
                new UserBuilder("MarkEditor",
                        USER_PASSWORD).withFullName("Mark Operator")
                        .withGrantedAuthority(RESOLUTION_READ_AUTHORITY)
                        .withGrantedAuthority(RESOLUTION_WRITE_AUTHORITY).build();

        User liesd =
                new UserBuilder("hasread",
                        USER_PASSWORD)
                        .withFullName("Liesd von Selbst")
                        .withGrantedAuthority(RESOLUTION_READ_AUTHORITY).build();

        User admin =
                new UserBuilder("admin", USER_PASSWORD)
                        .withFullName("Adminus Dominskus")
                        .withGrantedAuthority(RESOLUTION_ADMIN_AUTHORITY).build();

        this.userRepository.save(admin);
        this.userRepository.save(edEditor);
        this.userRepository.save(liesd);
        this.userRepository.save(markEditor);
    }
}
