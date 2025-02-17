package in.testuniversity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByMobile(String phone);
	Optional<User> findByEmailId(String email);
	Optional<User> findByEmailIdOrMobile(String emailId, String mobile);

}
