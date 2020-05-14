package project.akhir.danapprentechteam3.login.payload.response;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private Long saldo;

}
