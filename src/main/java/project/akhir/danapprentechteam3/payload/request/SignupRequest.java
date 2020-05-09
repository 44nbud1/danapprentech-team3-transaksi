package project.akhir.danapprentechteam3.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    private String noTelepon;
 
    @NotBlank
    private String email;

    @Length(min = 6)
    @NotBlank
    private String password;

    @NotBlank
    @Column
    private String confirmPassword;

    @NotBlank
    @Size(max = 20)
    @Column
    private String namaUser;

}
