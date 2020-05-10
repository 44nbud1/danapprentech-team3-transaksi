package project.akhir.danapprentechteam3.security.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.payload.request.SmsOtp;
import project.akhir.danapprentechteam3.repository.SmsOtpRepository;

import java.util.Random;

@Service
public class SmsOtpServiceImpl implements SmsOtpService
{

    // aan ACb3c4fe3afb030fc4975038ed77135694
    // 0daa8a0588e12aa01507690d0ac8fc61

    //eduardus AC215bf02aef9c9f5534e8da999e182f00
    // ceb6c45a047285eb21ea36a8b43abdd2
    // +12057549917

    // Asti AC23886956f90a67a68538383ef1c6c028
    //3f0f1fd0dfe3cb476af04a91f3782208
    //+12019756910

    public static final String ACCOUNT_SID = "ACb3c4fe3afb030fc4975038ed77135694";
    public static final String AUTH_TOKEN = "0daa8a0588e12aa01507690d0ac8fc61";

    @Autowired
    private SmsOtpRepository smsOtpRepository;

    public String createOtp()
    {
        Random rand = new Random();
        String newCodeOtp = "";
        for (int i=0; i<4; i++)
        {
            newCodeOtp += rand.nextInt(9);
        }

        SmsOtp smsOtp = new SmsOtp();
        smsOtpRepository.save(smsOtp);
        return newCodeOtp;
    }

    @Override
    public Message sendSMS(String noTelepon, String otp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(noTelepon),
                new com.twilio.type.PhoneNumber("+19416769743"),
                "Your Otp is "+ otp)
                .create();

        return message;
    }
}
