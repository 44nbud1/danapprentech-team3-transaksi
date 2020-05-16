package project.akhir.danapprentechteam3.login.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
public class ForgotPassword<U, L extends Number> {

    private Long idReset;

    private String token ;

    private String otp ;

    private boolean statusOtp ;

    private Date createdDate;

    private String email;
    private String noTelepon;

    private String newPassword;

    private String confirmPassword;

    public ForgotPassword()
    {
        createdDate = new Date();
        token = UUID.randomUUID().toString();
    }
}
