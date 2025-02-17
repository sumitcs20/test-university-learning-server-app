package in.testuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
}
