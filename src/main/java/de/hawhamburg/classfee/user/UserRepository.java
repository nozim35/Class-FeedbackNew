package de.hawhamburg.classfee.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByName(String name);
}