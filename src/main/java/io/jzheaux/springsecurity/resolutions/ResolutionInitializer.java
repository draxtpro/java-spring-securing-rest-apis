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

        User edEditor = new User("EdEditor",
                USER_PASSWORD);
        edEditor.setFullName("Ed Schreiberlesling");
        edEditor.grantAuthority(RESOLUTION_READ_AUTHORITY);
        edEditor.grantAuthority(RESOLUTION_WRITE_AUTHORITY);

        User markEditor = new User("MarkEditor",
                USER_PASSWORD);
        markEditor.setFullName("Mark Operator");
        markEditor.grantAuthority(RESOLUTION_READ_AUTHORITY);
        markEditor.grantAuthority(RESOLUTION_WRITE_AUTHORITY);

        User liesd = new User("hasread",
                USER_PASSWORD);
        liesd.setFullName("Liesd von Selbst");
        liesd.grantAuthority(RESOLUTION_READ_AUTHORITY);

        User admin = new User("admin",USER_PASSWORD);
        admin.setFullName("Adminus Dominskus");
        admin.grantAuthority(RESOLUTION_ADMIN_AUTHORITY);

        this.userRepository.save(admin);
        this.userRepository.save(edEditor);
        this.userRepository.save(liesd);
        this.userRepository.save(markEditor);
    }
}
