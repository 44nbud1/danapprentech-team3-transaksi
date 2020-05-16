package project.akhir.danapprentechteam3.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.login.models.EmailToken;

public interface EmailVerify extends JpaRepository<EmailToken, Long>
{
    EmailToken findByCodeVerify(String code);
    EmailToken findByMobileNumber(String mobileNumber);
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
    void deleteByEmail(String Email);
    void deleteByMobileNumber(String mobileNumber);
    EmailToken findByEmail(String email);
}
