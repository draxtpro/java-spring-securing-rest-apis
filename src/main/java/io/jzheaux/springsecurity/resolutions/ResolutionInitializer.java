package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
    private static final String USER_PASSWORD = "{bcrypt}$2a$12$.dSqQCYlYTYTOhYMR.LQsOu4cdGn5q8p/LywFIARfmJp6EGG.cEBK";
	private static final String RESOLUTION_READ_AUTHORITY = "resolution:read";
	private static final String RESOLUTION_WRITE_AUTHORITY = "resolution:write";
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

        User hasBoth = new User("user",
                USER_PASSWORD);
        hasBoth.grantAuthority(RESOLUTION_READ_AUTHORITY);
        hasBoth.grantAuthority(RESOLUTION_WRITE_AUTHORITY);

        User hasWrite = new User("haswrite",
                USER_PASSWORD);
        hasWrite.grantAuthority(RESOLUTION_WRITE_AUTHORITY);

        User hasRead = new User("hasread",
                USER_PASSWORD);
        hasRead.grantAuthority(RESOLUTION_READ_AUTHORITY);

        this.userRepository.save(hasBoth);
        this.userRepository.save(hasRead);
        this.userRepository.save(hasWrite);
    }
}
