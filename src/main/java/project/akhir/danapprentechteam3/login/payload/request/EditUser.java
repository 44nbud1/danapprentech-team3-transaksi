package project.akhir.danapprentechteam3.login.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUser
{
    private String namaUser;
    private String email;
    private String noTelepon;
}
