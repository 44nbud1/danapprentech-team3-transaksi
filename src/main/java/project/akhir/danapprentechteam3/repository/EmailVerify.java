package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.payload.request.EmailOtp;

public interface EmailVerify extends JpaRepository<EmailOtp, Long>
{
    EmailOtp findByCodeVerify(String code);
    EmailOtp findByEmail(String email);
}
