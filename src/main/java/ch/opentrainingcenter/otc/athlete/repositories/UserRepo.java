package ch.opentrainingcenter.otc.athlete.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.opentrainingcenter.otc.athlete.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
