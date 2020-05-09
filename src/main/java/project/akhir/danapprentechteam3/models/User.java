package project.akhir.danapprentechteam3.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column
	private String username;

	@NotBlank
	@Size(max = 20)
	@Column
	private String namaUser;

	@NotBlank
	@Size(max = 50)
	@Column
	private String email;

	@NotBlank
	@Column
	private String password;

	@Transient
	private Set<Role> roles = new HashSet<>();
}
