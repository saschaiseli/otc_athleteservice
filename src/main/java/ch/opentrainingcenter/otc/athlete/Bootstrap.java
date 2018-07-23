package ch.opentrainingcenter.otc.athlete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ch.opentrainingcenter.otc.athlete.domain.User;
import ch.opentrainingcenter.otc.athlete.repositories.UserRepo;
import ch.opentrainingcenter.otc.athlete.service.UserService;

import javax.validation.constraints.NotNull;

@Component
@Slf4j
public class Bootstrap {

    @NotNull
    private UserService service;

    public Bootstrap(@Autowired UserService service) {

        this.service = service;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        log.info("Context refreshed....");
        User user = new User();
        user.setFirstname("Sascha");
        user.setLastname("Iseli");
        service.saveUser(user);
    }
}
