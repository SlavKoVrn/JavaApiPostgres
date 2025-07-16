package skillsrock.apiusers.repository;

import skillsrock.apiusers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}