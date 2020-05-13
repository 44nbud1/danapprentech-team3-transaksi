package project.akhir.danapprentechteam3.login.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

	private String noTelepon;
	private String password;
	private String newPassword;

}
