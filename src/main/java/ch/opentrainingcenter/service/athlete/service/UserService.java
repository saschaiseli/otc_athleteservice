package ch.opentrainingcenter.service.athlete.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.service.athlete.domain.User;
import ch.opentrainingcenter.service.athlete.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @NonNull
    private UserRepo repo;

    public UserService(@Autowired UserRepo repo) {

        this.repo = repo;
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public User createUser(User user) {
        return repo.save(user);
    }
}
