package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	void deleteByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
