package project.akhir.danapprentechteam3.login.payload.request;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "email_token")
public class EmailToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmailOtp;
    private String codeVerify;
    private String email;
    private String mobileNumber;
    private boolean statusEmailVerify;

}

