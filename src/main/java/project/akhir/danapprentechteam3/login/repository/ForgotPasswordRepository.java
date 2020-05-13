package project.akhir.danapprentechteam3.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.login.models.User;
import project.akhir.danapprentechteam3.login.payload.request.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {

    User findByEmail(String email);

}
