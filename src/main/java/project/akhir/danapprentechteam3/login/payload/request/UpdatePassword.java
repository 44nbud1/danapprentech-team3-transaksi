package project.akhir.danapprentechteam3.login.payload.request;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
public class UpdatePassword {

        private Long idReset;
        private String token ;

        private String otp ;

        private boolean statusOtp ;

        private Date createdDate;

        private String email;
        private String noTelepon;

        @Transient
        private String newPassword;

        @Transient
        private String confirmPassword;

        public UpdatePassword()
        {
                createdDate = new Date();
                token = UUID.randomUUID().toString();
        }
}
