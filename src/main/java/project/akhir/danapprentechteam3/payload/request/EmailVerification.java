package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;
import project.akhir.danapprentechteam3.models.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class EmailVerification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private boolean statusEmail = false;

    public EmailVerification()
    {
        statusEmail = true;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

}
