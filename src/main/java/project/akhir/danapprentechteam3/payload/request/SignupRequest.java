package project.akhir.danapprentechteam3.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;
 
    @NotBlank
    private String email;
    
    private Set<String> role;

    @Length(min = 6)
    @NotBlank
    private String password;

}
