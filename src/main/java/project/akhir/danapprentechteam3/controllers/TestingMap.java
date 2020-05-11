package project.akhir.danapprentechteam3.controllers;

import project.akhir.danapprentechteam3.payload.request.SmsOtp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestingMap {


    public static void main(String[] args) {

//        String mobileNumber = "+6285777488828";
//
//        Map<String, SmsOtp> otpData = new HashMap<>();
//
//        SmsOtp otp = new SmsOtp();
////		otp.setMobileNumber(mobileNumber);
//        otp.setMobileNumber("+6285777488828");// dummy
////		otp.setCodeOtp(smsOtpService.createOtp());
//        otp.setCodeOtp("0657"); // dummy
//        otp.setExpirytime(System.currentTimeMillis()+18000);
//        otpData.put(mobileNumber,otp);
//
//        System.out.println(otpData.get("+6285777488828").getMobileNumber());

//        int i = 5;
//        int a = 0;
//        while (i>0){
//            System.out.println("Remaining: "+i+" seconds");
//            try {
//                i--;
//                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
//            }
//            catch (InterruptedException e) {
//                //I don't think you need to do anything for your particular problem
//            }
//            if (i == 0)
//            {
//                a =100;
//            }
//
//        }
//
////        System.out.println(a);
//        Long waktu = System.currentTimeMillis()/1000;
//
//        while (waktu != 0)
//        {
//            System.out.println(waktu);
//        }
        Random rand = new Random();
        String newCodeOtp = "";
        for (int i=0; i<4; i++)
        {
            newCodeOtp += rand.nextInt(9);
        }

        System.out.println(newCodeOtp);

    }
}