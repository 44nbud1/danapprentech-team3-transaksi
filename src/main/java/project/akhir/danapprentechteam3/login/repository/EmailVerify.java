package project.akhir.danapprentechteam3.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.login.payload.request.EmailOtp;

public interface EmailVerify extends JpaRepository<EmailOtp, Long>
{
    EmailOtp findByCodeVerify(String code);
    EmailOtp findByMobileNumber(String mobileNumber);
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
    void deleteByEmail(String Email);
    void deleteByMobileNumber(String mobileNumber);
}
