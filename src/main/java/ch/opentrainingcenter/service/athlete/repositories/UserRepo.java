package ch.opentrainingcenter.service.athlete.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.service.athlete.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
