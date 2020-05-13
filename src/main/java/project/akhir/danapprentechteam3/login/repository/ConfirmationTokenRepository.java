package project.akhir.danapprentechteam3.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.login.payload.request.EmailVerification;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<EmailVerification,Long> {
    EmailVerification findByConfirmationToken(String confirmationToken);
    void deleteByConfirmationToken(String token);
}
