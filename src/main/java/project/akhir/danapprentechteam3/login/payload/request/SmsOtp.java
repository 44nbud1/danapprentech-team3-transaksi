package project.akhir.danapprentechteam3.login.payload.request;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SmsOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSmsOtp;
    private String codeOtp;
    private String email;
    private String mobileNumber;
    private boolean statusOtp;

}
