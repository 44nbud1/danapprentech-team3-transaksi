package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class EmailOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmailOtp;
    private String codeVerify;
    private String email;
    private String mobileNumber;
    private boolean statusEmailVerify;

}

