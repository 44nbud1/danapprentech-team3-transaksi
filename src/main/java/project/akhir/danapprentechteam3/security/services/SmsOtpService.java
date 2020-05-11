package project.akhir.danapprentechteam3.security.services;

import com.twilio.rest.api.v2010.account.Message;

public interface SmsOtpService {
    Message sendSMS(String noTelepon,String otp);
    boolean countDownt();
}
