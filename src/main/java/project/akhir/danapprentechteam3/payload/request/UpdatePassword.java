package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class UpdatePassword {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
