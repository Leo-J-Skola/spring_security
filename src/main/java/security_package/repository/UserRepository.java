package security_package.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import security_package.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
