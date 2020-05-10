package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.models.User;
import project.akhir.danapprentechteam3.payload.request.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {

    User findByEmail(String email);

}
