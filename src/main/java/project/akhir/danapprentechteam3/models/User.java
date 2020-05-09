package project.akhir.danapprentechteam3.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(	name = "users")

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = User.class)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column
	private String noTelepon;

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

	@NotBlank
	@Column
	private String virtualAccount ;

	@JsonIgnore
	@Transient
	private Set<Role> roles = new HashSet<>();

	@Transient
	private String message ;

	@Transient
	private String status ;

}
