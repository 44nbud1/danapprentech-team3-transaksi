package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.akhir.danapprentechteam3.models.User;

public interface ForgotPasswordRepository extends JpaRepository<User,Long> {

    @Modifying
    @Query("update User u set u.password = :password where u.email = :email")
    void updatePassword(@Param("password") String password, @Param("email") String email);

}
