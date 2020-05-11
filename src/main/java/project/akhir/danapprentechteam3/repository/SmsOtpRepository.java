package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.payload.request.SmsOtp;

@Repository
public interface SmsOtpRepository extends JpaRepository<SmsOtp,Long>
{
    SmsOtp findByMobileNumber(String otp);
   void deleteByMobileNumber(String mobileNumber);
   SmsOtp findByCodeOtp(String otp);
}
