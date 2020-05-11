package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ForgotPassword<U, L extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reset")
    private Long idReset;
    @Column(name = "token_reset_password")
    private String token ;

    private String otp ;

    @Column
    private boolean statusOtp ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String email;
    private String noTelepon;

    @Transient
    private String newPassword;

    @Transient
    private String confirmPassword;

    public ForgotPassword()
    {
        createdDate = new Date();
        token = UUID.randomUUID().toString();
    }
}
