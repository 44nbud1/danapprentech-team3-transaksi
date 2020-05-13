package project.akhir.danapprentechteam3.login.controllers;

import project.akhir.danapprentechteam3.login.payload.request.EmailVerification;

import java.util.*;

public class Testing {
    public static void main(String[] args) {
         Map<String, EmailVerification> signUpMap = new HashMap<>();
         String signupKeyVal = "";

        // email verify
        signupKeyVal = "+6285777488828";

        EmailVerification confirmationToken = new EmailVerification();

        confirmationToken.setStatusEmail(true);
        confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
        confirmationToken.setCreatedDate(new Date());
        signUpMap.put(signupKeyVal,confirmationToken);


        System.out.println(signUpMap.get("+6285777488828").getConfirmationToken());
    }

}