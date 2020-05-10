package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {


	private String noTelepon;


	private String password;

}
